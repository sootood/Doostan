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
    Core core;

    public BasketAdapter(Context context, LinkedList<BasketData> loads, BasketAdapter.IViewHolderClicks itemClickListner) {
        this.context = context;
        this.loads = loads;
        listener = itemClickListner;
        Logger.d(loads);
        core = new Core(context);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textPrice, textCategory, txtCode, carGroup, countPak;
        public CardView cardbasket;

        public ViewHolder(View view) {
            super(view);
            textName = (TextView) view.findViewById(R.id.toolsName);
            textPrice = (TextView) view.findViewById(R.id.price);
//            textCategory = (TextView) view.findViewById(R.id.carCat);
            txtCode = (TextView) view.findViewById(R.id.code);
            cardbasket = (CardView) view.findViewById(R.id.cardBasket);
            countPak = (TextView) view.findViewById(R.id.countPak);
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final BasketData data = loads.get(position);

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
    }

    @Override
    public int getItemCount() {
        return loads.size();
    }

    public interface IViewHolderClicks {
        void onTextClick(CategoryData v, int pos);
    }
}