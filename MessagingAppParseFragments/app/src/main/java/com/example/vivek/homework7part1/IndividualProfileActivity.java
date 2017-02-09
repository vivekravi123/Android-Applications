package com.example.vivek.homework7part1;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;

public class IndividualProfileActivity extends AppCompatActivity {

    String userId, UserName, toName;
    TextView fname, lname;
    String image;
    ImageView logOut, send, gallery, profileimage;
    EditText message;
    DatabaseReference mDatabasefrom, mUsers,mDatabaseTo;
    private FirebaseAuth mAuth;
    ArrayList<Message> messageslist = new ArrayList<>();
    MessageAdapter adapter;
    FirebaseStorage storage =  FirebaseStorage.getInstance();
    StorageReference storageRef;
    ListView individualuserlistview;
    Message currentMessages;

    String firname,lasname;
String toId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_profile);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Messages Conversation");

        storageRef = storage.getReferenceFromUrl("gs://fir-94c2c.appspot.com");


        toId = getIntent().getExtras().getString("id");
        Log.d("demo", "to id is"+toId);




        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fname = (TextView) findViewById(R.id.textViewfirstNameindivi);
        lname = (TextView) findViewById(R.id.textViewlastNameindivi);

        logOut = (ImageView) findViewById(R.id.imageLogout);
        send = (ImageView) findViewById(R.id.imageSend);
        gallery = (ImageView) findViewById(R.id.imageGallery);
        profileimage = (ImageView) findViewById(R.id.profilepicindivi);
        individualuserlistview = (ListView) findViewById(R.id.individualuser_listView);

        mAuth = FirebaseAuth.getInstance();
        mDatabasefrom = FirebaseDatabase.getInstance().getReference().child("messages").child(userId).child(toId);
        mDatabaseTo = FirebaseDatabase.getInstance().getReference().child("messages").child(toId).child(userId);
        mUsers = FirebaseDatabase.getInstance().getReference().child("user");

        message = (EditText) findViewById(R.id.editTextMessageind);


        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    String id = d.getKey();
                    if(id.equals(toId)){
                        User user = d.getValue(User.class);

                        toName = user.getFname();
                        fname.setText(user.getFname());
                        lname.setText(user.getLname());
                        image = user.getImageUrl();
                        Picasso.with(IndividualProfileActivity.this).load(image).into(profileimage);


                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren()) {
                            String id = d.getKey();
                            if(id.equals(toId)){
                           User user = d.getValue(User.class);

                              

                            }
                        }
                    }
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                Log.d("name",userId);
                String key = mDatabasefrom.child("messages").child(userId).push().getKey();


                String keyid = userId+key;


                Message newMsg = new Message(keyid,"TEXT",message.getText().toString(),"read",date, toId, UserName,toName );
                newMsg.setImageUrl("null");
                newMsg.setUserName(UserName);
                newMsg.setTouser(toId);

                Log.d("demo", newMsg.getTouser());

                Map<String, Object> expenseValues = newMsg.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(userId+key, expenseValues);

                mDatabasefrom.updateChildren(childUpdates);

                messageslist.add(newMsg);



                String keys = mDatabaseTo.child("messages").child(toId).push().getKey();

                String keyid3 =toId+keys;

                Message newMsg3 = new Message(keyid3,"TEXT",message.getText().toString(),"unread",date, userId,UserName, toName);
                newMsg3.setImageUrl("null");
                newMsg3.setUserName(UserName);
                newMsg3.setTouser(userId);



                Map<String, Object> expenseValues1 = newMsg3.toMap();
                Map<String, Object> childUpdates1 = new HashMap<>();
                childUpdates1.put(toId+keys, expenseValues1);

                mDatabaseTo.updateChildren(childUpdates1);


                Log.d("demo","to id is "+toId);

                individualprofileconversation adapter = new individualprofileconversation(IndividualProfileActivity.this,R.layout.individualmsgconlistview, messageslist);
                individualuserlistview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                pictureIntent.setDataAndType(data, "image/*");
                startActivityForResult(pictureIntent,20);

                individualprofileconversation adapter = new individualprofileconversation(IndividualProfileActivity.this,R.layout.individualmsgconlistview, messageslist);
                individualuserlistview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(IndividualProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mDatabasefrom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {

                    String id = d.getKey();
                    currentMessages = d.getValue(Message.class);
//currentMessages.setTouser(toId);


                    messageslist.add(currentMessages);
                    Log.d("demo", "list size init== " +currentMessages.getTouser()       +messageslist.size()+"");



                    }

                individualprofileconversation adapter = new individualprofileconversation(IndividualProfileActivity.this,R.layout.individualmsgconlistview, messageslist);
                individualuserlistview.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 20){
                Uri imageURI = data.getData();
                String path = "images/" + userId + key + ".jpeg";
                StorageReference imageRef = storageRef.child(path);
                imageRef.putFile(imageURI)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri url = taskSnapshot.getDownloadUrl();
                                Log.d("demo", url.toString());
                                /*String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                                Message newmsg = new Message();
                                newmsg.setCreatedAt(date);
                                newmsg.setImageUrl(url.toString());
                                newmsg.setMessage("null");
                                newmsg.setId(userId+key);
                                newmsg.setType("IMAGE");
                                newmsg.setUserName(UserName);

                                Map<String, Object> expenseValues = newmsg.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(userId+key, expenseValues);

                                mDatabase.updateChildren(childUpdates);*/


                                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                                Log.d("name",userId);
                                String key = mDatabasefrom.child("messages").child(userId).push().getKey();


                                String keyid = userId+key;


                                Message newMsg = new Message(keyid,"IMAGE",null,"read",date, toId, UserName, toName);
                                newMsg.setImageUrl(url.toString());
                                newMsg.setUserName(UserName);
                                newMsg.setTouser(toId);

                                Log.d("demo", newMsg.getTouser());

                                Map<String, Object> expenseValues = newMsg.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(userId+key, expenseValues);

                                mDatabasefrom.updateChildren(childUpdates);

                                messageslist.add(newMsg);



                                String keys = mDatabaseTo.child("messages").child(toId).push().getKey();

                                String keyid3 =toId+keys;

                                Message newMsg3 = new Message(keyid3,"IMAGE",null,"unread",date, userId, UserName, toId);
                                newMsg3.setImageUrl(url.toString());
                                newMsg3.setUserName(UserName);
                                newMsg3.setTouser(userId);



                                Map<String, Object> expenseValues1 = newMsg3.toMap();
                                Map<String, Object> childUpdates1 = new HashMap<>();
                                childUpdates1.put(toId+keys, expenseValues1);

                                mDatabaseTo.updateChildren(childUpdates1);


                            }
                        });
            }
        }
    }
}
