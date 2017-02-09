package com.example.vivek.midterm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lax on 10/24/2016.
 */
public class AppAdapter extends ArrayAdapter<App> {
    Context mcontext;
    int mresource;
    List<App> newslist;
    DatabaseDataManager dm;
    public AppAdapter(Context context, int resource, List<App> objects) {

        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        newslist=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource, parent, false);
        }

        ImageView image= (ImageView) convertView.findViewById(R.id.imageView_app);
        ImageView cost= (ImageView) convertView.findViewById(R.id.imageView_price);
        TextView title= (TextView) convertView.findViewById(R.id.textview_name);
        TextView price= (TextView) convertView.findViewById(R.id.textview_cost);
        App app=newslist.get(position);
        Picasso.with(mcontext).load(app.getImage()).into(image);
        title.setText(app.getName());
        price.setText(app.getCost());
        Log.d("demo",app.getCost());
        double co= Double.parseDouble(app.getCost().substring(1,app.getCost().length()));
        if(co<2.00)
        {
            Picasso.with(mcontext).load(R.drawable.price_low).into(cost);
        }
        if(co>=2.00&&co<6.00)
        {
            Picasso.with(mcontext).load(R.drawable.price_medium).into(cost);
        }

        if(co>=6.00)
        {
            Picasso.with(mcontext).load(R.drawable.price_high).into(cost);
        }
        notifyDataSetChanged();

        return convertView;
    }
}
