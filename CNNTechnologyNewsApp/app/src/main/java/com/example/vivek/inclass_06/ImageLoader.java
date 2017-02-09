package com.example.vivek.inclass_06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Nikita on 9/26/2016.
 */
public class ImageLoader extends AsyncTask<String,Void,Bitmap> {
    int counter=0;
    String title;
    @Override
    protected Bitmap doInBackground(String... params) {

        String first=null;
        Bitmap image=null;
        counter=Integer.parseInt(params[0]);
        title=params[2];
        // first=arrayLists[0].get(0);
        InputStream in=null;
        URL url = null;
        try {
            url = new URL(params[1]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            in=con.getInputStream();
            image= BitmapFactory.decodeStream(in);
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        MainActivity.iv[counter].setImageBitmap(bitmap);
        Log.d("flag in imageload",String.valueOf(MainActivity.flag));
        MainActivity.ll.addView(MainActivity.iv[counter]);
        MainActivity.tv[counter].setText(title);
        MainActivity.ll.addView(MainActivity.tv[counter]);
        MainActivity.iv[counter].setId(counter);
       MainActivity.pbar.dismiss();






    }
}
