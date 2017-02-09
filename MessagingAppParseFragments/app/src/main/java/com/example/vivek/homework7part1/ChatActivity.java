package com.example.vivek.homework7part1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.name;

public class ChatActivity extends AppCompatActivity {


    String userId, imageurl, username;
    TextView fname;
    TextView lname, gender;
    ImageView profileimage, logOut;
    ListView userslistview;
    //RecyclerView friendslistrecycler;
    DatabaseReference mDatabase, mUsers,mUserslist;
    private FirebaseAuth mAuth;
    FirebaseStorage storage =  FirebaseStorage.getInstance();
    StorageReference storageRef;


    ArrayList<User> userlist;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Home");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fname = (TextView) findViewById(R.id.textViewfirstName);
        lname = (TextView) findViewById(R.id.textViewlastName);
        logOut = (ImageView) findViewById(R.id.imageLogout);
        profileimage = (ImageView) findViewById(R.id.profilepic);
        gender = (TextView) findViewById(R.id.textViewgender);
        userslistview = (ListView) findViewById(R.id.user_listView);


        /*friendslistrecycler = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        friendslistrecycler.setLayoutManager(linearLayoutManager);*/

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("userlist");
        mUsers = FirebaseDatabase.getInstance().getReference().child("user");
        mUserslist = FirebaseDatabase.getInstance().getReference().child("user");


        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    String id = d.getKey();
                    if(id.equals(userId)){
                        user = d.getValue(User.class);
                      fname.setText(user.getFname().toString());
                        lname.setText(user.getLname().toString());
                        imageurl = user.getImageUrl();
                        gender.setText(user.getGender());
                        Picasso.with(ChatActivity.this).load(imageurl).into(profileimage);


                        
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUserslist.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userslistview = (ListView) findViewById(R.id.user_listView);
                userlist = new ArrayList<User>();
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    String id = d.getKey();
                    if(!(id.equals(userId))){
                        User friendslist = d.getValue(User.class);

                        userlist.add(friendslist);
                        Log.d("demo", userlist.size()+"");

                    }

                }

                UserlistAdapter adapter = new UserlistAdapter(ChatActivity.this,R.layout.userlistview, userlist);
                userslistview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_editProfile)
        {

            Intent intent=new Intent(ChatActivity.this,SignupActivity.class);

            intent.putExtra("userID",mAuth.getCurrentUser().getUid());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }




    public void InboxAction(View view) {

        Intent intentinbox = new Intent(ChatActivity.this, Inbox_Activity.class);
        intentinbox.putExtra("Userid", userId);
        startActivity(intentinbox);
    }
}
