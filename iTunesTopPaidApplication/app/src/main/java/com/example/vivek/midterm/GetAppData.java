package com.example.vivek.midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
 * Created by Lax on 10/24/2016.
 */
public class GetAppData extends AsyncTask<String, Void, ArrayList<App>> {
    DataRetreiver activity;
    ArrayList<App> apps=new ArrayList<App>();
    public GetAppData(DataRetreiver activity) {
        this.activity = activity;
    }
    @Override

    protected ArrayList<App> doInBackground(String... params) {
        String line;
        JSONObject jsonObject = null;
        try {
            URL url = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            reader.close();
            jsonObject = new JSONObject(sb.toString());

            return GetAppDetails(jsonObject);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("except","Json exception is : "+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        activity.setData(apps);
    }

    private ArrayList<App> GetAppDetails(JSONObject jsonObject) {
        try {
            JSONObject feed=jsonObject.getJSONObject("feed");
            JSONArray entry= (JSONArray) feed.get("entry");
            for(int i=0;i<entry.length();i++)
            {
                JSONObject name=entry.getJSONObject(i).getJSONObject("im:name");
                String label=name.getString("label");
                JSONArray images=entry.getJSONObject(i).getJSONArray("im:image");
                JSONObject image=images.getJSONObject(0);
                String imgurl=image.getString("label");
                JSONObject price=entry.getJSONObject(i).getJSONObject("im:price");
                String cost=price.getString("label");
                apps.add(new App(label,cost,imgurl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apps;
    }

    interface DataRetreiver {
        void setData(ArrayList<App> newsList);
    }
}
