package com.example.inclass09;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUP extends Activity implements SignUpAsync.iData{

    EditText fname, lname, email, pass, rpass;
    String token, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = (EditText) findViewById(R.id.signUPFirstName);
        lname = (EditText) findViewById(R.id.signUPLastName);
        email = (EditText) findViewById(R.id.signUPEmail);
        pass = (EditText) findViewById(R.id.signUPPassword);
        rpass = (EditText) findViewById(R.id.signUPRepeatPassword);

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",rpass.getText().equals(pass.getText())+"");

                new SignUpAsync(SignUP.this).execute(email.getText().toString(), pass.getText().toString(), fname.getText().toString(), lname.getText().toString());

            }
        });
    }

    @Override
    public void setToken(String s) {
        Log.d("de",s);
        try {
            JSONObject root = new JSONObject(s);
            status = root.getString("status");
            if(status.equals("ok")) {
                token = root.getString("token");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editior = preferences.edit();

                editior.putString("TOKEN",token);
                editior.commit();

                Intent intent = new Intent(this, Chat.class);
                startActivity(intent);
            } else
                Toast.makeText(this, root.getString("message"),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
