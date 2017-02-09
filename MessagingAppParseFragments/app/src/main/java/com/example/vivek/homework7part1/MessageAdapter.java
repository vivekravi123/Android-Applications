package com.example.vivek.homework7part1;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends ArrayAdapter{

    List<Message> messagelist ;
    Context mContext;
    int mResource;
    int i=0;
    String userName;
    String value;

    TextView chatMsg, chatTime, chatName, commentmsg;
    ImageView chatImage, comment, delete;

    EditText input;
    Message mMessage;

    String path, id,username;
    StorageReference imageRef;

    public MessageAdapter(Context context, int resource, List<Message> objects, String userName) {
        super(context, resource, objects);
this.mResource = resource;
        this.userName = userName;
        this.messagelist =  objects;
        this.mContext = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(mResource,parent,false);




        }

        final Message messge = messagelist.get(position);

        FirebaseStorage storage =  FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-94c2c.appspot.com");

        chatMsg = (TextView) convertView.findViewById(R.id.chatMessage);
        chatTime = (TextView) convertView.findViewById(R.id.sentTime);
        chatName = (TextView) convertView.findViewById(R.id.sender);
        chatImage = (ImageView) convertView.findViewById(R.id.chatImage);
        comment = (ImageView) convertView.findViewById(R.id.addComment);
        delete = (ImageView) convertView.findViewById(R.id.deleteChat);
        commentmsg = (TextView) convertView.findViewById(R.id.comment);




        PrettyTime p = new PrettyTime();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String date = messge.getCreatedAt();
        path = "images/" + messge.getId() + ".jpeg";
        imageRef = storageRef.child(path);

        chatName.setText(messge.getUserName());






        try {
            chatTime.setText(p.format(format.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }




            if(messge.getType().equals("TEXT")){
            chatMsg.setVisibility(View.VISIBLE);
            Log.d("message",messge.getMessage());
            chatMsg.setText(messge.getMessage());
            chatImage.setVisibility(View.GONE);
        } else if(messge.getType().equals("IMAGE")){
            chatMsg.setVisibility(View.GONE);
            chatImage.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(messge.getImageUrl()).into(chatImage);
        }






        convertView.findViewById(R.id.deleteChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              id= messge.getId();
                               Log.d("demo","delete" + id);
                FirebaseDatabase.getInstance().getReference().child("messages").child(id).removeValue();
                Log.d("demo","delete after" + id);
                if(messge.getType().equals("IMAGE")){
                    imageRef.delete();
                }
            }
        });



        convertView.findViewById(R.id.addComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id= messge.getId();
                input = new EditText(mContext);
                new AlertDialog.Builder(mContext)
                        .setTitle("Comment")
                        .setMessage("Enter your comment here")
                        .setView(input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                 value = input.getText().toString();

                                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                                String key = FirebaseDatabase.getInstance().getReference().child("messages").child(id).child("comments").push().getKey();
                                Message comment = new Message();
                                comment.setMessage(value);
                                comment.setCreatedAt(date);
                                comment.setUserName(username);


                                FirebaseDatabase.getInstance().getReference().child("messages").child(id).child("comments").child(key).setValue(comment);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();

                commentmsg.setText(value);

            }
        });

return convertView;

    }}
