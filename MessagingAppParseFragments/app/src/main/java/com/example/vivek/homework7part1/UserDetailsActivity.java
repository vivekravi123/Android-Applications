package com.example.vivek.homework7part1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {


    TextView firstname, lastname, gender;
    ImageView profilephoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);





        firstname = (TextView) findViewById(R.id.tfirstName);
         lastname = (TextView) findViewById(R.id.tlastName);
        gender = (TextView) findViewById(R.id.tgender);
        profilephoto = (ImageView) findViewById(R.id.profilephoto);


        firstname.setText(getIntent().getExtras().getString("fname"));
        lastname.setText(getIntent().getExtras().getString("lname"));
        gender.setText(getIntent().getExtras().get("gender").toString());
        Picasso.with(this).load(getIntent().getExtras().get("imageurl").toString()).into(profilephoto);
    }
}
