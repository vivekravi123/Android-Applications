package com.example.inclass09;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpAsync extends AsyncTask<String, Void, String>{

    iData data;

    SignUpAsync(iData data){
        this.data = data;
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... params) {
        Log.d("shdgf",params[0]);

        RequestBody formBody = new FormBody.Builder()
                    .add("fname", params[2])
                    .add("lname", params[3])
                    .add("email", params[0])
                    .add("password", params[1])
                    .build();

        Request request = new Request.Builder()
                    .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/signup")
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .post(formBody)
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
        data.setToken(s);
    }

    public interface iData{
        public void setToken(String s);
    }
}
