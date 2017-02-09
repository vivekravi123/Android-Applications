package com.example.vivek.inclass_06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsDetails extends AppCompatActivity {

    News news;
    TextView title,date,desc;
    static ImageView img;
    Date d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

//SimpleDateFormat dateFormat=new SimpleDateFormat("EEE dd MM yyyy kk:mm:ss zzzz",Locale.ENGLISH);


        news = (News) getIntent().getSerializableExtra(MainActivity.NEWS_LIST_KEY);
//        try {
//            d= dateFormat.parse("Tue 06 Sep 2016 12:16:51 GMT");
//            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//           Log.d("date",fmtOut.format(d));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.pubDate);
        img = (ImageView) findViewById(R.id.imageView);
        desc = (TextView) findViewById(R.id.desc);
        date.setText(news.getPubdate());
        title.setText(news.getTitle());
        desc.setText(news.getDescription());
        new GetLargeImage().execute(news.getLargeimage());








    }
}
