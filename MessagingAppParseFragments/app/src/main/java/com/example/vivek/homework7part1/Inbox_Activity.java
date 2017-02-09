package com.example.vivek.homework7part1;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;

public class Inbox_Activity extends AppCompatActivity {

    String userId, UserName;
    TextView fname, lname;
    ImageView logOut, send, gallery;
    EditText message;
    DatabaseReference mDatabase, mUsers;
    private FirebaseAuth mAuth;
    ArrayList<Message> messages;
    MessageAdapter adapter;
    FirebaseStorage storage =  FirebaseStorage.getInstance();
    StorageReference storageRef;
    ListView messagelistview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Messages");

        storageRef = storage.getReferenceFromUrl("gs://fir-94c2c.appspot.com");


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fname = (TextView) findViewById(R.id.textViewfirstName);
        lname = (TextView) findViewById(R.id.textViewlastName);

        logOut = (ImageView) findViewById(R.id.imageLogout);
        send = (ImageView) findViewById(R.id.imageSend);
        gallery = (ImageView) findViewById(R.id.imageGallery);
        message = (EditText) findViewById(R.id.editTextMessage);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");
        mUsers = FirebaseDatabase.getInstance().getReference().child("user");
        messagelistview = (ListView) findViewById(R.id.msg_listView);



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();

                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    Message expense = d.getValue(Message.class);
                    //Log.d("DATASNPSHOT",expense.getUserName());
                    messages.add(expense);
                }

              /*  if(messages.size() > 0)
                    message.setVisibility(View.GONE);
                else
                    message.setVisibility(View.VISIBLE);*/

                Log.d("size",messages.size()+"");
                MessageAdapter madapter = new MessageAdapter(Inbox_Activity.this,R.layout.messsage_itemlayout, messages, UserName);
                messagelistview.setAdapter(madapter);
                madapter.setNotifyOnChange(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    String id = d.getKey();
                    if(id.equals(userId)){
                        User user = d.getValue(User.class);
                        UserName = user.getFname();
                        fname.setText(UserName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            } });




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message newMsg = new Message();
                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                Log.d("name",userId);
                String key = mDatabase.child("messages").child(userId).push().getKey();

                newMsg.setType("TEXT");
                newMsg.setMessage(message.getText().toString());
                newMsg.setImageUrl("null");
                newMsg.setCreatedAt(date);
                newMsg.setId(userId+key);
                newMsg.setUserName(UserName);

                Map<String, Object> expenseValues = newMsg.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(userId+key, expenseValues);

                mDatabase.updateChildren(childUpdates);
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
            }
        });




        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Inbox_Activity.this, ChatActivity.class);
                startActivity(intent);
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
                                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
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

                                mDatabase.updateChildren(childUpdates);
                            }
                        });
            }
        }
    }
}
