package com.example.vivek.midterm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.DailyHolder> {

    static ArrayList<App> fiveDayForecast;
    String temp_unit;

    public RecycleViewAdapter(ArrayList<App> fiveDayForecast, String unit) {

        this.fiveDayForecast = fiveDayForecast;
        temp_unit= unit;
    }

    @Override
    public RecycleViewAdapter.DailyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview, parent, false);
        return new RecycleViewAdapter.DailyHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.DailyHolder holder, int position) {
        App dayForecast = fiveDayForecast.get(position);
        holder.bindData(dayForecast,temp_unit);
    }

    @Override
    public int getItemCount() {
        return fiveDayForecast.size();
    }

    public static class DailyHolder extends RecyclerView.ViewHolder {

        TextView cost, temp;
        ImageView icon,price;
        View itemView;
        ArrayList<App> al;
        final static String LIST = "LIST";
        final static String LIST_LIST = "LIST_LIST";

        public DailyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            temp = (TextView) itemView.findViewById(R.id.textview_name_rec);
            cost = (TextView) itemView.findViewById(R.id.textview_cost_rec);
            icon = (ImageView) itemView.findViewById(R.id.imageView_app_rec);
            price = (ImageView) itemView.findViewById(R.id.imageView_price_recy);
        }


        public void bindData(App list, String units){
            temp.setText(list.getName());
            cost.setText(list.getCost());
            Picasso.with(itemView.getContext()).load(list.getImage()).into(icon);
            double co= Double.parseDouble(list.getCost().substring(1,list.getCost().length()));
            if(co<2.00)
            {
                Picasso.with(itemView.getContext()).load(R.drawable.price_low).into(price);
            }
            if(co>=2.00&&co<6.00)
            {
                Picasso.with(itemView.getContext()).load(R.drawable.price_medium).into(price);
            }

            if(co>=6.00)
            {
                Picasso.with(itemView.getContext()).load(R.drawable.price_high).into(price);
            }
        }


    }
}
