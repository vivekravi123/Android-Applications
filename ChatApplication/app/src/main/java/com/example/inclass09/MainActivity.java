package com.example.inclass09;

/* Vivek Ravi
 Group 37
 inclass 9
*
* */



import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        email = (EditText) findViewById(R.id.editTextName);
        pass = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUP.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody formBody = new FormBody.Builder()
                        .add("email", email.getText().toString())
                        .add("password", pass.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/login")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);
                            String responseString = responseBody.string();
                            JSONObject root = new JSONObject(responseString);
                            String status = root.getString("status");
                            String token = root.getString("token");

                            if(status.equals("ok")){
                                token = root.getString("token");
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editior = preferences.edit();

                                editior.putString("TOKEN",token);
                                editior.commit();

                                Intent intent = new Intent(MainActivity.this, Chat.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(MainActivity.this, root.getString("message"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}