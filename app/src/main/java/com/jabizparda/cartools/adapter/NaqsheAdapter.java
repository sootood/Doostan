package com.jabizparda.cartools.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jabizparda.cartools.Core;
import com.jabizparda.cartools.R;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.lid.lib.LabelImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 4/21/2018.
 */

public class NaqsheAdapter extends RecyclerView.Adapter<NaqsheAdapter.ViewHolder> {

    private Context context;
    private List<NaqsheSokhtData> loads;
    private NaqsheAdapter.IViewHolderClicks listener;

    public NaqsheAdapter(Context context, List<NaqsheSokhtData> load, NaqsheAdapter.IViewHolderClicks itemClickListner) {
        this.context = context;
        this.loads = load;
        listener = itemClickListner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_naqshe, parent, false);
        NaqsheAdapter.ViewHolder viewHolder = new NaqsheAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NaqsheSokhtData data = loads.get(position);
        holder.nameMaintence.setText(data.getTitleTool()==null ? "نا معلوم" : Core.toPersianStatic( String.valueOf(data.getTitleTool())));
        if (data.getImageTool()!=null){
            Glide.with(context).load(data.getImageTool())
                    .thumbnail(Glide.with(context).load(R.drawable.llyj))
                    .fitCenter()
                    .crossFade()
                    .into(holder.imageMaintence);
        }else{
            Glide.
                    with(context).
                    load("http://91.92.190.54:1095/images/logo.png")
                    .fitCenter()
                    .crossFade()
                    .into(holder.imageMaintence);
        }

        holder.imageMaintence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageClick(data.getImageTool(),position);


            }
        });
        holder.relatedToolsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRelatedBtnClicked(data,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loads.size();
    }

    public interface IViewHolderClicks {
        void onImageClick(String image,int pos);
        void onRelatedBtnClicked(NaqsheSokhtData data, int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameMaintence;
        ImageView imageMaintence;
        CardView relatedToolsCard;

        public ViewHolder(View view) {
            super(view);
            nameMaintence = (TextView) view.findViewById(R.id.nameMaintence);
            imageMaintence=(ImageView) view.findViewById(R.id.imageTools);
            relatedToolsCard=(CardView) view.findViewById(R.id.relatedTools);

        }
    }


}
