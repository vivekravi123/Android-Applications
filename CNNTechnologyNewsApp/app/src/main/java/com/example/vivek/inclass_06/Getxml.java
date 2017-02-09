package com.example.vivek.inclass_06;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vivek on 9/26/2016.
 */
public class Getxml extends AsyncTask<String,Void,ArrayList<News>>
{
    StringBuilder sb ;
    TextView tv1;
    ImageView iv1;
    setValues ref;

    public Getxml(setValues ref) {
        this.ref=ref;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return Parsexml.Parsexmlnews.Parsemethod(in);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        String line ="";
//        try {
//            while ((line = reader.readLine()) != null){
//                sb.append(line + "\n");
//                Log.d("demo",sb.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        super.onPostExecute(newses);
       // tv1=MainActivity.tv;
       // iv1=MainActivity.iv;
       // MainActivity.newslist=newses;

       // tv1.setText(newses.get(0).getTitle());
        ref.SetValues(newses);


        Log.d("to string",newses.toString());
    }

    public static interface setValues
    {
         public void SetValues(ArrayList<News> news);
    }


}
