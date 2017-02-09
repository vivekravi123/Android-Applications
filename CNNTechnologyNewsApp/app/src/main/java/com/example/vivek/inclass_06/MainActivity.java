package com.example.vivek.inclass_06;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Getxml.setValues {
    public static ProgressDialog pbar;
    public static ImageView iv[];
    public static TextView tv[];
    public static LinearLayout ll;
    public static LinearLayout l2;
    public static ScrollView sv;
    public static LinearLayout l3;
     ArrayList<News> newslist;
    public static int flag=1;
    int i;
    int k;

    public static final String NEWS_LIST_KEY = "NEWS_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newslist = new ArrayList<News>();
        pbar=new ProgressDialog(this);
        pbar.setMessage("Loading News...");
        pbar.show();
        l2 = (LinearLayout) findViewById(R.id.relative);
        sv = new ScrollView(this);
        sv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll = new LinearLayout(this);
        l3 = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        l3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        l3.setOrientation(LinearLayout.VERTICAL);
        new Getxml(this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
    }







    @Override
    public void SetValues(ArrayList<News> news) {
        newslist=news;
        Log.d("inside main",news.toString());
        tv=new TextView[newslist.size()];
        iv=new ImageView[newslist.size()];

        for(i=0;i<newslist.size();i++) {
            iv[i]=new ImageView(this);

            tv[i] = new TextView(this);
            new ImageLoader().execute(String.valueOf(i),newslist.get(i).getImage(),newslist.get(i).getTitle());
            Log.d("flag in main",String.valueOf(MainActivity.flag));
            final int index=i;
                        iv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("kvalue",String.valueOf(k));
                    Intent intent = new Intent(MainActivity.this,NewsDetails.class);
                    intent.putExtra(NEWS_LIST_KEY,newslist.get(index));
                    startActivity(intent);
                }
            });





        }

        sv.addView(ll);
        l2.addView(sv);
//        for(k=0;k<newslist.size();k++)
//        {
//
//            iv[k].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("kvalue",String.valueOf(k));
//                    Intent intent = new Intent(MainActivity.this,NewsDetails.class);
//                    intent.putExtra(NEWS_LIST_KEY,newslist.get(k-1));
//                    startActivity(intent);
//                }
//            });
//        }
    }
}
