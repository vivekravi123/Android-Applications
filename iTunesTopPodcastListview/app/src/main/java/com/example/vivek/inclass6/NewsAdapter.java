package com.example.vivek.inclass6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends ArrayAdapter<Feed> {

    List<Feed> mNews ;
    Context mContext;
    int mResource;
    int i=0;
    String size;
    public NewsAdapter(Context context, int resource, List<Feed> objects,String size) {
        super(context, resource, objects);
    this.mContext=context;
        this.mNews=objects;
        this.mResource=resource;
this.size=size;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(mResource,parent,false);
        }
final Feed feed=mNews.get(position);
        EditText title_text= (EditText) convertView.findViewById(R.id.main_title_editText);
        title_text.setText(feed.title);
        ImageView main_image= (ImageView) convertView.findViewById(R.id.main_imageView);
        Picasso.with(mContext).load(feed.thumbnail).into(main_image);
        Log.d("demo","image_birmap"+feed.thumbnail);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","itemclicked");
                Intent i1 = new Intent(mContext, FeedData.class);
                Bundle bundle=new Bundle();

                i1.putExtra("Title", feed.getTitle());
                i1.putExtra("Desc", feed.getDescription());
                i1.putExtra("Img", feed.getImage());
                Log.d("demo","image clicked"+feed.getImage());
                i1.putExtra("Pubdate", feed.getPubdate());

                mContext.startActivity(i1);
            }
        });
        if(size!=null) {
            if (feed.title.toLowerCase().contains(size.toLowerCase())) {
                Log.d("demo","title ="+feed.getTitle());
                convertView.setBackgroundColor(Color.RED);
            }
        }
        return convertView;
    }
}
