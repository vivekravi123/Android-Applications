package com.example.vivek.midterm;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsTable {
    static final String TABLENAME = "News";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_TITLE = "name";
    static final String COLUMN_DATE = "cost";
    static final String COLUMN_BLOCK = "image";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_TITLE + " text not null, ");
        sb.append(COLUMN_DATE + " text not null, ");
        sb.append(COLUMN_BLOCK + " text not null);");

        try{
            db.execSQL(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        NewsTable.onCreate(db);
    }
}
