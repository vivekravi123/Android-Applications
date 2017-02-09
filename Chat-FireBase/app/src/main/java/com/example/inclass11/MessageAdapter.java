package com.example.inclass11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.key;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>{

    private List<Messages> messages;
    Context context;
    String UserName;


    public MessageAdapter(List<Messages> filters, Context context, String UserName) {
        this.UserName = UserName;
        this.messages =  filters;
        this.context = context;
    }

    @Override
    public MessageAdapter.MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_layout, parent, false);
        return new MessageHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        Messages msg = messages.get(position);
        holder.bindCity(msg, UserName);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView chatMsg, chatTime, chatName;
        ImageView chatImage, comment, delete;
        View itemView;
        ListView listView;
        EditText input;
        Messages mMessage;
        FirebaseStorage storage =  FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://inclass11-dbb0f.appspot.com");

        String path, id,username;
        StorageReference imageRef;

        public MessageHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            chatMsg = (TextView) itemView.findViewById(R.id.chatMessage);
            chatTime = (TextView) itemView.findViewById(R.id.sentTime);
            chatName = (TextView) itemView.findViewById(R.id.sender);
            chatImage = (ImageView) itemView.findViewById(R.id.chatImage);
            comment = (ImageView) itemView.findViewById(R.id.addComment);
            delete = (ImageView) itemView.findViewById(R.id.deleteChat);
            listView = (ListView) itemView.findViewById(R.id.listView);

            delete.setClickable(true);
            comment.setClickable(true);
            delete.setOnClickListener(this);
            comment.setOnClickListener(this);
        }

        public void bindCity(Messages m, String UserName) {
            mMessage = m;
            username = UserName;

            PrettyTime p = new PrettyTime();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            String date = mMessage.getCreatedAt();
            path = "images/" + m.getId() + ".jpeg";
            imageRef = storageRef.child(path);

            chatName.setText(mMessage.getUserName());

            try {
                chatTime.setText(p.format(format.parse(date)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(m.getType().equals("TEXT")){
                chatMsg.setVisibility(View.VISIBLE);
                Log.d("message",mMessage.getMessage());
                chatMsg.setText(mMessage.getMessage());
                chatImage.setVisibility(View.GONE);
            } else if(m.getType().equals("IMAGE")){
                chatMsg.setVisibility(View.GONE);
                chatImage.setVisibility(View.VISIBLE);
                Picasso.with(context).load(mMessage.getImageUrl()).into(chatImage);
            }

//            FirebaseDatabase.getInstance().getReference().child("messages").child(id).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ArrayList<Messages> comments = new ArrayList<Messages>();
//                    for(DataSnapshot d: dataSnapshot.getChildren()) {
//                        comments.add(d.getValue(Messages.class));
//                    }
//
//                    CommentAdapter adapter = new CommentAdapter(context, R.layout.comment_item_layout, comments);
//                    listView.setAdapter(adapter);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            FirebaseDatabase.getInstance().getReference().child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot d: dataSnapshot.getChildren()) {
                        String key = d.getKey();
                        if(mMessage.getId().equals(key)){
                            id = key;
                            Log.d("demo", "idinside"+id);

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(v.getId() == R.id.deleteChat){

Log.d("demo", "id"+id);
                FirebaseDatabase.getInstance().getReference().child("messages").child(id).removeValue();

                if(mMessage.getType().equals("IMAGE")){
                    imageRef.delete();
                }
            } else if(v.getId() == R.id. addComment){
                input = new EditText(context);
                new AlertDialog.Builder(context)
                        .setTitle("Comment")
                        .setMessage("Enter your comment here")
                        .setView(input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value = input.getText().toString();
                                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                                String key = FirebaseDatabase.getInstance().getReference().child("messages").child(id).child("comments").push().getKey();
                                Messages comment = new Messages();
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
            }
        }
    }
}
