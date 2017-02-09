package com.example.vivek.homework7part1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.vivek.homework7part1.R.id.chatImage;


public class individualprofileconversation extends ArrayAdapter {

String id, path;
    TextView message, from, date;
    ImageView msgimage, deleteimg;
    Button msgbutton;
    StorageReference imageRef;
    DatabaseReference mDatabasefrom;
    DatabaseReference  mUsers;

    List<Message> mMessage;
    Context mContext;
    int mResource;
    int i = 0;
    Message msg;

    String userId;

    public individualprofileconversation(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);


        this.mContext = context;
        this.mMessage = objects;
        this.mResource = resource;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        convertView.setBackgroundColor(Color.WHITE);





        msg = mMessage.get(position);


        FirebaseStorage storage =  FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-94c2c.appspot.com");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

userId = mAuth.getCurrentUser().getUid();

        message = (TextView) convertView.findViewById(R.id.messagetext);
        from = (TextView) convertView.findViewById(R.id.messagefrom);
        msgimage = (ImageView) convertView.findViewById(R.id.profilepicindivi);
        date = (TextView) convertView.findViewById(R.id.textdate);

        deleteimg = (ImageView) convertView.findViewById(R.id.deleteimagge);

      // message.setText(msg.getMessage());
        date.setText(msg.getDate());
        //from.setText(msg.getFromname());
//        from.setText(msg.getToname());

        mUsers = FirebaseDatabase.getInstance().getReference().child("user").child(userId);

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                User user=dataSnapshot.getValue(User.class);
                String uid= user.getId();
                Log.d("demo", "user id in convert view is = "+uid);


                if(uid.equals(userId)) {
                    from.setText("me");

                }
                else
                {
                    from.setText(user.fname+" "+user.lname);

                }

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        path = "images/" + msg.getId() + ".jpeg";
        imageRef = storageRef.child(path);

if(msg.read.equals("unread"))
{
    String touser = msg.getTouser();

    id= msg.getId();

    Log.d("demo","testttttttt"+touser);

    Log.d("demo","testttttttt"+id);
    mDatabasefrom = FirebaseDatabase.getInstance().getReference().child("messages").child(userId).child(touser).child(id);
    convertView.setBackgroundColor(Color.BLUE);
    msg.setRead("read");

    Map<String, Object> expenseValues = msg.toMap();
    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put(msg.getId(), expenseValues);
    mDatabasefrom.updateChildren(childUpdates);


}

        if(msg.getType().equals("TEXT")){
            message.setVisibility(View.VISIBLE);
            Log.d("message",msg.getMessage());
            message.setText(msg.getMessage());
            msgimage.setVisibility(View.GONE);
        } else if(msg.getType().equals("IMAGE")){
            message.setVisibility(View.GONE);
            msgimage.setVisibility(View.VISIBLE);
            Log.d("image",msg.getImageUrl());

            Picasso.with(mContext).load(msg.getImageUrl()).into(msgimage);
        }

        convertView.findViewById(R.id.deleteimagge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String touser = msg.getTouser();

                id= msg.getId();
                Log.d("demo","delete" + touser);
                FirebaseDatabase.getInstance().getReference().child("messages").child(userId).child(touser).child(id).removeValue();

                Log.d("demo","delete after" + id);
                if(msg.getType().equals("IMAGE")){
                    imageRef.delete();
                }

                msg=mMessage.remove(position);
                notifyDataSetChanged();

            }
        });


return  convertView;
    }

}