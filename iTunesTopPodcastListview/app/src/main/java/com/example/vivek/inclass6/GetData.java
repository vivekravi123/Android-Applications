package com.example.vivek.inclass6;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class GetData extends AsyncTask<String,Void,ArrayList<Feed>> {
    DataRetreiver activity;

    @Override
    protected void onPostExecute(ArrayList<Feed> newsItems) {

        super.onPostExecute(newsItems);



        activity.setData(newsItems);
    }

    public GetData(DataRetreiver activity) {

        this.activity=activity;

    }

    @Override
    protected ArrayList<Feed> doInBackground(String... params) {

        try{
            URL url=new URL(params[0]);

            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode=con.getResponseCode();
            if(statuscode==HttpURLConnection.HTTP_OK)
            {
                Log.d("demo1"," @getdata3");
                InputStream inputStream= con.getInputStream();
                return FeedParser.ParseNews(inputStream);
            }






        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface DataRetreiver{
        void setData(ArrayList<Feed> newsList);
    }
}
