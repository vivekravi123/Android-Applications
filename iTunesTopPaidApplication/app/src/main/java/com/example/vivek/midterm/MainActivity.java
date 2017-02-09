package com.example.vivek.midterm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetAppData.DataRetreiver {
ProgressDialog progress;
    ListView listviews;
    Switch switch1;
    ArrayList<App> apps=new ArrayList<App>();
    ImageView refresh;
    DatabaseDataManager dm;
    RecyclerView recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        dm=new DatabaseDataManager(this);
        progress.setTitle("Loading Hourly Data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        listviews= (ListView) findViewById(R.id.listView);
        switch1= (Switch) findViewById(R.id.switch1);
        refresh= (ImageView) findViewById(R.id.imageView);
        recycle= (RecyclerView) findViewById(R.id.savedCities);
      LinearLayoutManager linear=  new LinearLayoutManager(recycle.getContext());
        recycle.setLayoutManager(linear);
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        //recycle.setLayoutManager();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetAppData(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
            ArrayList<App> refr= (ArrayList<App>) dm.getAllCities();
                for(int i=0;i<refr.size();i++)
                {
                    dm.deleteCity(refr.get(i));
                }
                RecycleViewAdapter recadapter=new RecycleViewAdapter((ArrayList<App>) dm.getAllCities(),"");
                recycle.setAdapter(recadapter);
                recadapter.notifyDataSetChanged();
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Collections.sort(apps, new Comparator<App>() {
                        @Override
                        public int compare(App fruit2, App fruit1)
                        {

                            return  fruit1.name.compareTo(fruit2.name);
                        }
                    });
                    setData(apps);
                }
                else
                {
                    Collections.sort(apps, new Comparator<App>() {
                        @Override
                        public int compare(App fruit2, App fruit1)
                        {

                            return  fruit1.name.compareTo(fruit2.name);
                        }
                    });
                    ArrayList<App> sorted=new ArrayList<App>();
                    ArrayList<App> newslist_sort=new ArrayList<>();
                    for(int i=apps.size()-1;i>=0;i--) {
                        newslist_sort.add(apps.get(i));
                    }
                    setData(newslist_sort);
                }
            }
        });
        new GetAppData(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        listviews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dm.saveCity(apps.get(i));
                apps.remove(i);
                setData(apps);
                RecycleViewAdapter recadapter=new RecycleViewAdapter((ArrayList<App>) dm.getAllCities(),"");
                recycle.setAdapter(recadapter);
                recadapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public void setData(ArrayList<App> Applist) {

        progress.dismiss();
        apps=Applist;

        AppAdapter adapter = new AppAdapter(this, R.layout.listview,apps);
        listviews.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }
}
