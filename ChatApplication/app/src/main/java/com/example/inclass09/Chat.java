package com.example.inclass09;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class Chat extends Activity implements ChatAsync.iData{

    private final OkHttpClient client = new OkHttpClient();
    ListView listView;
    MessagesAdapter adapter;
    ArrayList<Message> msgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.chatList);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editior = preferences.edit();
        final String token = preferences.getString("TOKEN","");

        new ChatAsync(this).execute(token);
    }

    @Override
    public void setList(ArrayList<Message> s) {
        msgs=s;
        adapter= new MessagesAdapter(Chat.this,R.layout.row_chat_item,msgs);
        listView.setAdapter(adapter);
    }
}
