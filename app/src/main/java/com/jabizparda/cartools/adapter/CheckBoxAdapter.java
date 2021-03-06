package com.jabizparda.cartools.adapter;

/**
 * Created by Karo on 7/5/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jabizparda.cartools.R;
import com.jabizparda.cartools.room.CategoryData;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
    private Context context;
    private LinkedList<CategoryData> loads;
    private IViewHolderClicks listener;
    private List<Model> mModelList;

    public CheckBoxAdapter(List<Model> modelList,Context context, LinkedList<CategoryData> loads, IViewHolderClicks itemClickListner) {
        this.context = context;
        this.loads = loads;
        listener = itemClickListner;
        Logger.d(loads);
        mModelList = modelList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        CheckBox checkBox;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.textCardCheck);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            linearLayout = (LinearLayout) view.findViewById(R.id.checkBoxLL);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_checckbox, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final CategoryData data = loads.get(position);
        final Model model = mModelList.get(position);
        final TextView text = viewHolder.text;

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    if (data.getStateSelect()==0){
                        model.setSelected(true);
                        data.setStateSelect(1);
                        listener.onTextClick(data, position);
                    }else {
                        model.setSelected(false);
                        data.setStateSelect(0);
                        listener.onTextClick(data, position);

                    }
                }
            }
        });

        text.setText(data.getCarCategory());
    }

    @Override
    public int getItemCount() {
        return loads.size();
    }

    public interface IViewHolderClicks {
        void onTextClick(CategoryData v, int pos);
    }
}