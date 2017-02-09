/*
vivek ravi -
        inclass07
*/

package com.example.vivek.inclass6;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetData.DataRetreiver,GetImage.imageInterface {

    ArrayList<Feed> feedItems;
    ArrayList<Feed> m_feedItems =new ArrayList<Feed>();
    ArrayList<Feed> unm_feedItems =new ArrayList<Feed>();
    ProgressDialog progress;
    Feed ni;
    String index;
    ListView news;
    EditText keyword;
Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        keyword= (EditText) findViewById(R.id.main_editText);
        progress =new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle("Loading News");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        new GetData(this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml");
        go= (Button) findViewById(R.id.Go_button);
    }


    public void clear_Action(View view) {


        keyword.setText("");
        news.setAdapter(null);
        NewsAdapter adapter = new NewsAdapter(this, R.layout.listview, feedItems, null);
        news.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        System.out.println(feedItems.toString());
    }
    @Override
    public void setData(ArrayList<Feed> newsList) {

        feedItems =newsList;
        news = (ListView) findViewById(R.id.news_listView);

        progress.dismiss();

        for(int i=0;i<newsList.size();i++){
            ni=newsList.get(i);

            new GetImage(this,ni).execute(ni.getThumbnail(),index);



        }
        news.setAdapter(null);
        NewsAdapter adapter=new NewsAdapter(this,R.layout.listview,newsList,null);
news.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        System.out.println(feedItems.toString());




    }

    @Override
    public void setImage(Bitmap bitmap, Feed feed) {
feed.setThumbnail_bitmap(bitmap);

        /*
        int ivId=Integer.parseInt(viewId);
        ImageView iview=(ImageView)findViewById(ivId);

        iview.setImageBitmap(bitmap);*/

    }

    public void go_click(View view) {

        m_feedItems=new ArrayList<Feed>();
        String key= keyword.getText().toString();
        Log.d("demo","feed size"+feedItems.size());
        int i=0;
        for(Feed keyw : feedItems)
        {
            if(keyw.getTitle().toLowerCase().contains(key.toLowerCase()))
            {
                m_feedItems.add(keyw);
                Log.d("demo","title demo"+keyw.getTitle());            i=i+1;
            }

        }
        for(Feed keyw : feedItems)
        {
            if(!(keyw.getTitle().toLowerCase().contains(key.toLowerCase())))
            {
                m_feedItems.add(keyw);
            }

        }

        news.setAdapter(null);
Log.d("demo", "i" +i);
        NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.listview,m_feedItems,key);
        news.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


    }
}
