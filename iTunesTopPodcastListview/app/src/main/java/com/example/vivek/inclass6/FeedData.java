package com.example.vivek.inclass6;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FeedData extends AppCompatActivity implements GetImage.imageInterface{
    Feed news;
    Feed item;
    TextView title, date, desc;
    ImageView img;
    String st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_data);
        title = (TextView) findViewById(R.id.title_tv);
        date = (TextView) findViewById(R.id.pubdate_tv);
        desc = (TextView) findViewById(R.id.story_tv);
        img= (ImageView) findViewById(R.id.imageView_feed);

        if (getIntent().getExtras() != null) {




            title.setText(getIntent().getExtras().get("Title").toString());
/*
            SimpleDateFormat fromUser = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM a");
            try {

                st = myFormat.format(fromUser.parse(getIntent().getExtras().get("Pubdate").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            date.setText(getIntent().getExtras().get("Pubdate").toString());

            desc.setText(getIntent().getExtras().get("Desc").toString());

        Picasso.with(this).load(getIntent().getExtras().get("Img").toString()).into(img);

            //new GetImage(this).execute(item.getImage(), id);
        }
    }


    @Override
    public void setImage(Bitmap bitmap, Feed viewId) {
        img.setImageBitmap(bitmap);
    }
}
