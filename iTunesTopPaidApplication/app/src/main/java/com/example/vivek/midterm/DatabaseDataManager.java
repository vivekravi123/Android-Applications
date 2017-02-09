package com.example.vivek.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NewsDAO newsDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        newsDAO = new NewsDAO(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public NewsDAO getCitiesDAO(){
        return this.newsDAO;
    }

    public long saveCity(App feed){
        return this.newsDAO.save(feed);
    }

    public boolean updateCity(App city){
        Log.d("demo","inside update");
        return this.newsDAO.update(city);
    }

    public boolean deleteCity(App city){
        return this.newsDAO.delete(city);
    }

    public App getCity(long id){
        return this.newsDAO.get(id);
    }

    public List<App> getAllCities(){
        return this.newsDAO.getAll();
    }
}
