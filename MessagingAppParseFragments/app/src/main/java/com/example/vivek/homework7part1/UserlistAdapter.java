package com.example.vivek.homework7part1;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserlistAdapter extends ArrayAdapter {

    TextView firstname, lastname, gender;
    ImageView profilepic;
    Button msgbutton;


    List<User> mUser ;
    Context mContext;
    int mResource;
    int i=0;

    public UserlistAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);

        this.mContext=context;
        this.mUser=objects;
        this.mResource=resource;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(mResource,parent,false);
        }

        final  User user = mUser.get(position);

        firstname = (TextView) convertView.findViewById(R.id.firstname);
        lastname = (TextView) convertView.findViewById(R.id.lastname);
        profilepic = (ImageView) convertView.findViewById(R.id.profileimage);
        gender = (TextView) convertView.findViewById(R.id.gender);
        msgbutton = (Button) convertView.findViewById(R.id.messagebutton);


        firstname.setText(user.getFname());
        lastname.setText(user.getLname());
        gender.setText(user.getGender());

        Log.d("demo", user.getImageUrl());
        Picasso.with(mContext).load(user.getImageUrl()).into(profilepic);


        msgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentinbox = new Intent(mContext, IndividualProfileActivity.class);
                intentinbox.putExtra("fname", user.getFname());
                intentinbox.putExtra("lname", user.getLname());
                intentinbox.putExtra("gender", user.getGender());
                intentinbox.putExtra("imageurl",user.getImageUrl());
                intentinbox.putExtra("id", user.getId());
                mContext.startActivity(intentinbox);


                            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","itemclicked");
                Intent i1 = new Intent(mContext, UserDetailsActivity.class);
                // Bundle bundle=new Bundle();

                i1.putExtra("fname", user.getFname());
                i1.putExtra("lname", user.getLname());
                i1.putExtra("gender", user.getGender());
                i1.putExtra("imageurl",user.getImageUrl());


                mContext.startActivity(i1);
            }
        });
        return convertView;
}}
