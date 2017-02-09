package com.example.inclass11;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Messages> {
    List<Messages> mData;
    Context mContext;
    int mResource;

    public CommentAdapter(Context context, int resource, List<Messages> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Messages podcast = mData.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.commentMessage);
        TextView name = (TextView) convertView.findViewById(R.id.senderComment);
        TextView dateView = (TextView) convertView.findViewById(R.id.sentTimeComment);

        title.setText(podcast.getMessage());
        name.setText(podcast.getUserName());
        PrettyTime p = new PrettyTime();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String date = podcast.getCreatedAt();

        try {
            dateView.setText(p.format(format.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
