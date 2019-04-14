package com.jabizparda.cartools;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jabizparda.cartools.adapter.CheckBoxAdapter;
import com.jabizparda.cartools.adapter.Model;
import com.jabizparda.cartools.room.CategoryData;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 123 on 3/11/2018.
 */

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private Context context;
    private LinkedList<BasketData> loads;
    private BasketAdapter.IViewHolderClicks listener;
    int type;
    Core core;
    float textSize;

    public BasketAdapter(Context context, LinkedList<BasketData> loads,int type,float textSize,BasketAdapter.IViewHolderClicks itemClickListner) {
        this.context = context;
        this.loads = loads;
        this.type=type;
        listener = itemClickListner;
        Logger.d(loads);
        core = new Core(context);
        this.textSize=textSize;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textPrice, textCategory, txtCode, carGroup, countPak;
        public CardView cardbasket;
        public ImageButton closeBtn;
        public ImageButton minusBtn;
        public ImageButton plusBtn;

        public ViewHolder(View view) {
            super(view);
            textName = (TextView) view.findViewById(R.id.toolsName);
            textPrice = (TextView) view.findViewById(R.id.price);
//            textCategory = (TextView) view.findViewById(R.id.carCat);
            txtCode = (TextView) view.findViewById(R.id.code);
            cardbasket = (CardView) view.findViewById(R.id.cardBasket);
            countPak = (TextView) view.findViewById(R.id.countPak);
            closeBtn = (ImageButton) view.findViewById(R.id.cancelImage);
            minusBtn = (ImageButton) view.findViewById(R.id.minusBtnImg);
            plusBtn = (ImageButton) view.findViewById(R.id.plusBtnImg);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.basket_card, parent, false);
        BasketAdapter.ViewHolder viewHolder = new BasketAdapter.ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final BasketData data = loads.get(position);

        viewHolder.textName.setTextSize(textSize);
        viewHolder.textPrice.setTextSize(textSize);
        viewHolder.txtCode.setTextSize(textSize);
        viewHolder.countPak.setTextSize(textSize);

        if (type==1){
            viewHolder.closeBtn.setVisibility(View.VISIBLE);
            viewHolder.plusBtn.setVisibility(View.VISIBLE);
            viewHolder.minusBtn.setVisibility(View.VISIBLE);
        }

        if (type==1 && position == 0){
            viewHolder.closeBtn.setVisibility(View.INVISIBLE);
            viewHolder.plusBtn.setVisibility(View.GONE);
            viewHolder.minusBtn.setVisibility(View.GONE);
        }


        if(position == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.cardbasket.setCardBackgroundColor(context.getColor(R.color.colorAccentTranse));
            }

        }

        viewHolder.textName.setText(Core.toPersianStatic(data.getChoosenName().equals(null) ? "نامعلوم" : data.getChoosenName()));
        if (data.choosenPrice == null)
            viewHolder.textPrice.setVisibility(View.INVISIBLE);
        else
            viewHolder.textPrice.setText(Core.toPersianStatic(data.getChoosenPrice() == null ? "نامعلوم" : data.getChoosenPrice()));
//        viewHolder.carGroup.setText(Core.toPersianStatic(data.getChoosenDetailGroup().equals(null) ? "نامعلوم" : data.getChoosenDetailGroup())  );
        viewHolder.countPak.setText(Core.toPersianStatic(String.valueOf(data.getChoosenCountMaintence() == null ? "نامعلوم" : data.getChoosenCountMaintence())));

        viewHolder.txtCode.setText(Core.toPersianStatic(data.getChoosenCode() == null ? "نامعلوم" : data.getChoosenCode()));
//            viewHolder.textCategory.setText(Core.toPersianStatic(data.getChoosenGroup().equals(null) ? "نامعلوم" : data.getChoosenGroup())  );
//        }

        viewHolder.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deletOrder(data,position);
            }
        });

        viewHolder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.plusCount(data,position);
            }
        });
        viewHolder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.minusCount(data,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return loads.size();
    }

    public interface IViewHolderClicks {
        void onTextClick(CategoryData v, int pos);
        void deletOrder(BasketData tools,int pos);
        void minusCount(BasketData tools,int pos);
        void plusCount(BasketData tools,int pos);
    }
}