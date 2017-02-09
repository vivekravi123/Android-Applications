package com.example.vivek.midterm;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    private SQLiteDatabase db;


    public NewsDAO(SQLiteDatabase db){
        this.db = db;
    }

    public long save(App feed){
        ContentValues values = new ContentValues();
        values.put(NewsTable.COLUMN_TITLE, feed.getName());
        values.put(NewsTable.COLUMN_DATE, feed.getCost());
        values.put(NewsTable.COLUMN_BLOCK, feed.getImage());
        return db.insert(NewsTable.TABLENAME, null, values);
    }

    public boolean update(App feed){
        ContentValues values = new ContentValues();

        values.put(NewsTable.COLUMN_TITLE, feed.getName());
        values.put(NewsTable.COLUMN_DATE, feed.getCost());
        values.put(NewsTable.COLUMN_BLOCK, feed.getImage());
        return db.update(NewsTable.TABLENAME, values, NewsTable.COLUMN_ID + "= ?", new String[]{feed.getId()+""}) > 0;
    }

    public boolean delete(App feed){
        return db.delete(NewsTable.TABLENAME, NewsTable.COLUMN_ID + "=?", new String[]{feed.getId()+""}) > 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public App get(long id){
        App city = null;
        Cursor c = db.query(true, NewsTable.TABLENAME, new String[] {
                        NewsTable.COLUMN_ID, NewsTable.COLUMN_TITLE, NewsTable.COLUMN_DATE,
                        NewsTable.COLUMN_BLOCK },
                NewsTable.COLUMN_ID + "= ?" ,
                new String[] {id + ""}, null, null, null, null, null);

        if (c != null && c.moveToFirst()){
            city = buidCityFromCursor(c);
            if (!c.isClosed()){
                c.close();
            }
        }
        return city;
    }

    public List<App> getAll(){
        List<App> newslist = new ArrayList<App>();
        Cursor c = db.query(NewsTable.TABLENAME, new String[] {
                        NewsTable.COLUMN_ID,  NewsTable.COLUMN_TITLE, NewsTable.COLUMN_DATE,
                        NewsTable.COLUMN_BLOCK},
                null, null, null, null, null);

        if (c != null && c.moveToFirst()){
            do {
                App city = buidCityFromCursor(c);
                if(city != null){
                    newslist.add(city);
                }
            } while (c.moveToNext());

            if (!c.isClosed()){
                c.close();
            }
        }
        return newslist;
    }

    public App buidCityFromCursor(Cursor c){
        App feed = null;
        if(c != null){
            feed = new App();
            feed.setId(c.getInt(0));
            feed.setName(c.getString(1));
            feed.setCost(c.getString(2));
            feed.setImage(c.getString(3));
        }
        return feed;
    }
}
