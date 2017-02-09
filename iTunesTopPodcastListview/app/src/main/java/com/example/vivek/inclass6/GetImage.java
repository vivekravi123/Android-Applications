package com.example.vivek.inclass6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetImage extends AsyncTask<String,Void,Bitmap> {
    Bitmap bit;
    imageInterface activity;
    String id;
    Feed feed;

    public GetImage(imageInterface activity,Feed feed) {
        this.activity=activity;this.feed=feed;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url= null;
        try {
            url = new URL(params[0]);
            id =params[1];
            HttpURLConnection con= (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            bit = BitmapFactory.decodeStream(con.getInputStream());
            return bit;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    interface imageInterface{

        void setImage(Bitmap bitmap, Feed viewId );
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);

        activity.setImage(bitmap, feed);
    }
}
