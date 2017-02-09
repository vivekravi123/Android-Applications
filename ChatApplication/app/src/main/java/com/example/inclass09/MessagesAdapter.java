package com.example.inclass09;

import android.content.Context;
import android.util.Log;
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

public class MessagesAdapter extends ArrayAdapter<Message> {

    List<Message> msgs;
    Context mContext;
    int mResource;

    public MessagesAdapter(Context context, int resource, List<Message> msgs) {
        super(context, resource, msgs);
        this.msgs = msgs;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Message message = msgs.get(position);

        TextView text = (TextView) convertView.findViewById(R.id.chatText);
        TextView name = (TextView) convertView.findViewById(R.id.chatName);
        TextView time = (TextView) convertView.findViewById(R.id.chatTime);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        name.setText(message.getFname() + " " + message.getLname());

        PrettyTime p = new PrettyTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = message.getDate();

        try {
            time.setText(p.format(format.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(message.getType().equals("TEXT")){
            text.setVisibility(View.VISIBLE);
            text.setText(message.getComment());
            image.setVisibility(View.GONE);
        } else if(message.getType().equals("IMAGE")){
            image.setVisibility(View.VISIBLE);
            Log.d("demo","http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/"+message.getThumbnail());
            Picasso.with(mContext).load("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/"+message.getThumbnail()).into(image);
            text.setVisibility(View.GONE);
        }
        return convertView;
    }

}
