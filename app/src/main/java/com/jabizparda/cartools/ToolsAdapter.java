package com.jabizparda.cartools;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.MyViewHolder>{
    List<Tools> list;

    private final static String persian_numbers[] = {"۰" , "۱" , "۲" ,"۳" , "۴" ,"۵" , "۶" , "۷"  , "۸" , "۹" };

    public static String replace(String str){
        String string = str;
        for(int i = 0 ; i < 10 ; i++){
            string = string.replace( i +"" , persian_numbers[i]);

        }
        return string;
    }


    public ToolsAdapter(List<Tools> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main_row, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(layoutParams);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tools Tools = list.get(position);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        holder.linearLayout.setLayoutParams(param);
        if(position == 0){
            holder.number.setTypeface(holder.number.getTypeface(), Typeface.BOLD);
            holder.number.setText("شماره");
            holder.type.setTypeface(holder.type.getTypeface(), Typeface.BOLD);
            holder.type.setText("نوع");
            holder.terminal.setTypeface(holder.terminal.getTypeface(), Typeface.BOLD);
            holder.terminal.setText("ترمینال");
            holder.time.setTypeface(holder.time.getTypeface(), Typeface.BOLD);
            holder.time.setText("زمان");
            holder.count.setTypeface(holder.count.getTypeface(), Typeface.BOLD);
            holder.count.setText("تعداد");
        } else {
            holder.number.setText(replace(Tools.getcode()) + "-" + replace(Tools.getimage()));
            holder.type.setText(replace(Tools.getType()));
            holder.terminal.setText(replace(Tools.getcode()));
            holder.time.setText(replace(Tools.getDate()));
            holder.count.setText(replace(Tools.getCount()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView type;
        TextView terminal;
        TextView time;
        TextView count;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
//            number = (TextView) itemView.findViewById(R.id.number);
//            type = (TextView) itemView.findViewById(R.id.type);
//            terminal = (TextView) itemView.findViewById(R.id.terminal);
//            time = (TextView) itemView.findViewById(R.id.time);
//            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
//            count = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
