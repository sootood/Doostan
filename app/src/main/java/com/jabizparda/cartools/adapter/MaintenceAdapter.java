
package com.jabizparda.cartools.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.jabizparda.cartools.Core;
import com.jabizparda.cartools.R;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.lid.lib.LabelImageView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MaintenceAdapter extends RecyclerView.Adapter<MaintenceAdapter.ViewHolder> {
    private Context context;
    private List<MaintenceDataSAvingVErsion> loads;
    private List<MaintenceDataSAvingVErsion> loadsAfterSearch;
    private IViewHolderClicks listener;
    ArrayList<Integer> itemSelected;
    private List<Model> mModelList;

    public MaintenceAdapter(List<Model> modelList, Context context, List<MaintenceDataSAvingVErsion> loads
            , IViewHolderClicks itemClickListner) {
        this.context = context;
        this.loads = loads;
        listener = itemClickListner;
        mModelList = modelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carCategory;
        TextView nameMaintence;
        TextView pricemaintence;
        TextView typemaintence;
        TextView countMaintence;
        LabelImageView imageMaintence;
        CardView cardView;
        CardView cardPrice;
        LinearLayout llCArd;

        public ViewHolder(View view) {
            super(view);
            carCategory = (TextView) view.findViewById(R.id.carCategory);
            nameMaintence = (TextView) view.findViewById(R.id.nameMaintence);
            pricemaintence = (TextView) view.findViewById(R.id.pricceMaintence);
//            codeMaintence = (TextView) view.findViewById(R.id.codeMaintence);
            typemaintence = (TextView) view.findViewById(R.id.typeCategory);
            countMaintence = (TextView) view.findViewById(R.id.countMaintence);
            imageMaintence = (LabelImageView) view.findViewById(R.id.imageTools);
            cardView = (CardView) view.findViewById(R.id.cardMaintence);
            llCArd = (LinearLayout) view.findViewById(R.id.llCard);
            cardPrice = (CardView) view.findViewById(R.id.cardPrice);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        itemSelected = new ArrayList<Integer>();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_maintence, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final MaintenceDataSAvingVErsion data = loads.get(position);
        final Model model = mModelList.get(position);
        Logger.d(data);
        Logger.e(String.valueOf(position));
        viewHolder.typemaintence.setText(Core.toPersianStatic(String.valueOf(data.getCarCategory())));
        viewHolder.nameMaintence.setText(data.getNameMaintence() == null ? "نا معلوم" : Core.toPersianStatic(String.valueOf(data.getNameMaintence())));
        if (data.getPricemaintence() == null) {
            viewHolder.cardPrice.setVisibility(View.GONE);
        } else {
            viewHolder.pricemaintence.setText(data.getPricemaintence() == null ? "نا معلوم" : Core.toPersianStatic(data.getPricemaintence()) + "ریال");

        }
        viewHolder.cardView.setBackgroundColor(model.isSelected() ? ContextCompat.getColor(context, R.color.colorAccentLight) : Color.WHITE);
        viewHolder.countMaintence.setText(Core.toPersianStatic(String.valueOf(data.getCountMaintence())));
        viewHolder.carCategory.setText(Core.toPersianStatic(String.valueOf(data.getTypeMaintence())));
        Logger.d(data.getImageMaintence());
        if (data.getImageMaintence() != null) {

            Glide.with(context).load(data.getImageMaintence())
                    .thumbnail(Glide.with(context).load(R.drawable.llyj))
                    .fitCenter()
                    .crossFade()
                    .into(viewHolder.imageMaintence);

        } else {
            Glide.
                    with(context).
                    load("http://91.92.190.54:1095/images/logo.png")
                    .fitCenter()
                    .crossFade()
                    .into(viewHolder.imageMaintence);
        }

        viewHolder.imageMaintence.setLabelText(Core.toPersianStatic(data.getCodeMaintence() == null ? "نامشخص" : String.valueOf(data.getCodeMaintence())));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                itemSelected.add(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
                if (listener != null) {
                    if (model.isSelected()) {
                        data.setStateSelect(1);
                        listener.onToolClick(data, itemSelected.get(0));
                        Logger.e("clicked");
                    } else {
                        data.setStateSelect(0);
                        listener.onToolClick(data, itemSelected.get(0));

                    }
                }
            }
        });


        viewHolder.imageMaintence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageClick(data.getImageMaintence(), position);


            }
        });

//        if(data.taxiState == 1){
//            viewHolder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow));
//        } else {
//            viewHolder.card.setCardBackgroundColor(Color.WHITE);
//        }

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Light.ttf");
//        text.setTypeface(font);
    }

    @Override
    public int getItemCount() {
        return loads.size();
    }

    public interface IViewHolderClicks {
        void onToolClick(MaintenceDataSAvingVErsion v, int pos);

        void onImageClick(String image, int pos);
//        void onToolDoubleClick(MaintenceData v, int pos);
    }

    public void filter(String text, LinkedList<MaintenceDataSAvingVErsion> allLoad) {
        Logger.d(allLoad);
        loadsAfterSearch = new ArrayList<>();
        if (text.isEmpty()) {
            loads.clear();
            loads.addAll(allLoad);
            notifyDataSetChanged();

        } else {
//            text = text.toLowerCase();
            for (int i = 0; i < loads.size(); i++) {
                if (loads.get(i).getNameMaintence().contains(text)) {
                    loadsAfterSearch.add(loads.get(i));

                } else {
                }
            }
            if (loadsAfterSearch.size() != 0) {
                loads.clear();
                loads.addAll(loadsAfterSearch);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "محصولی موجود نیست", Toast.LENGTH_LONG).show();

            }
        }

    }
}