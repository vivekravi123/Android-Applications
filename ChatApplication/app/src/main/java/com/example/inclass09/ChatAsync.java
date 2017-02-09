package com.example.inclass09;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChatAsync extends AsyncTask<String, Void, String>{

    ChatAsync.iData data;

    ChatAsync(ChatAsync.iData data){
        this.data = data;
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {
        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/messages")
                .addHeader("Authorization", "BEARER "+params[0])
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
            String responseString = s;
        JSONObject root = null;
        ArrayList<Message> msgs = new ArrayList<Message>();

        try {
            root = new JSONObject(responseString);

            JSONArray messages = root.getJSONArray("messages");

            for(int i = 0; i< messages.length(); i++) {
                Message msg = new Message();

                JSONObject object = messages.getJSONObject(i);
                msg.setFname(object.getString("UserFname"));
                msg.setLname(object.getString("UserLname"));
                msg.setComment(object.getString("Comment"));
                msg.setType(object.getString("Type"));
                msg.setThumbnail(object.getString("FileThumbnailId"));
                msg.setDate(object.getString("CreatedAt"));

                msgs.add(msg);
            }
            Log.d("demo1",msgs.size()+"");
            data.setList(msgs);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public interface iData{
        public void setList(ArrayList<Message> s);
    }
}
