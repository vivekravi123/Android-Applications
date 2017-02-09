package com.example.vivek.homework7part1;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;
import static com.example.vivek.homework7part1.R.id.profileimage;

public class SignupActivity extends AppCompatActivity {


    EditText emails, fname, lname, password, repeatpassword;
    public static final int imageintent = 100;

    private RadioGroup genderGroup;
    Button btnSubmit, btnCancel;

    private FirebaseAuth mAuth;

    String userId;

    ImageView imageView;

    User user = new User();
    FirebaseStorage storage =  FirebaseStorage.getInstance();
    StorageReference storageRef;


    DatabaseReference  mUsers,mUserslist;
    private DatabaseReference mDatabase;
String imageurl;
    String firstVal ="";
    String lastVal = "";
    String gender = "Male";
    String email ="";
    String cPWd ="";
    String pwdVal ="";
    String imageURL= "";
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsers = FirebaseDatabase.getInstance().getReference().child("user");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        genderGroup = (RadioGroup) findViewById(R.id.RadioGroupIDgender);

        emails = (EditText) findViewById(R.id.EditTextEmailID);
        fname = (EditText) findViewById(R.id.fNameID);
        lname = (EditText) findViewById(R.id.LNameID);
        password = (EditText) findViewById(R.id.PasswordID);
        repeatpassword = (EditText) findViewById(R.id.CPasswordID);
        final RadioButton male =(RadioButton)findViewById(R.id.MaleID);
        final RadioButton female =(RadioButton)findViewById(R.id.femaleID);

        imageView = (ImageView) findViewById(R.id.ProfilePicID);



        storageRef = storage.getReferenceFromUrl("gs://fir-94c2c.appspot.com");

        if(getIntent().getExtras()!=null)
        {


            Button p1_button = (Button)findViewById(R.id.signupbutton);
            p1_button.setText("Save Profile");

             userid=  getIntent().getExtras().getString("userID");

            mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot d: dataSnapshot.getChildren()) {
                        String id = d.getKey();
                        if(id.equals(userid)){
                            user = d.getValue(User.class);
                            fname.setText(user.getFname());
                            lname.setText(user.getLname());
                            emails.setText(user.getEmail());
                            password.setText(user.getPassword());
                            repeatpassword.setText(user.getPassword());
                            imageurl = user.getImageUrl();
                            gender = user.getGender();

                            if(gender.equals("male"))
                            {
                                male.setChecked(true);
                            }
else
                            {
                                female.setChecked(true);
                            }
                            //User user1=new User(fname.getText().toString(),lname.getText().toString(),emails.getText().toString(),password.getText().toString(),gender,);
                            Picasso.with(SignupActivity.this).load(imageurl).into(imageView);

                            //mUsers.child(userid).setValue(user);

                        }
                    }
                }
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }



        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.MaleID) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
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


    }




    public void SignupAction(View view) {

        firstVal = fname.getText().toString();
        lastVal = lname.getText().toString();
        pwdVal = password.getText().toString();
        cPWd = repeatpassword.getText().toString();
        email = emails.getText().toString();


        if (firstVal.equals("") || email.equals("") || pwdVal.equals("") || cPWd.equals("")) {
            Toast.makeText(getApplicationContext(), "Value is empty", Toast.LENGTH_SHORT).show();

        } else if (!pwdVal.equals(cPWd)) {
            Toast.makeText(getApplicationContext(), "Pwds do not match", Toast.LENGTH_SHORT).show();
        } else if (email!= "" && pwdVal!= "" ){

            mAuth.createUserWithEmailAndPassword(email, pwdVal)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("demo", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                if(getIntent().getExtras()!=null)
                                {
                                    userId = mAuth.getCurrentUser().getUid();

                                    //   Toast.makeText(getApplicationContext(), "User has been Created", Toast.LENGTH_SHORT).show();
                                    user.setEmail(email);
                                    user.setFname(firstVal);
                                    user.setLname(lastVal);
                                    user.setGender(gender);
                                    user.setPassword(pwdVal);
                                    user.setId(userId);

                                    mDatabase.child("user").child(userId).setValue(user);

                                }
                                Intent start = new Intent();
                                setResult(RESULT_CANCELED, start);

                                finish();
                            } else {

                               // user = new User();
                                userId = task.getResult().getUser().getUid();

                                //   Toast.makeText(getApplicationContext(), "User has been Created", Toast.LENGTH_SHORT).show();
                                 user.setEmail(email);
                                 user.setFname(firstVal);
                                 user.setLname(lastVal);
                                 user.setGender(gender);
                                 user.setPassword(pwdVal);
                                user.setId(userId);

                                mDatabase.child("user").child(userId).setValue(user);


                                Intent start = new Intent();
                                setResult(RESULT_OK, start);
                                finish();

                            }

                        }

                    });
        } else {
            Toast.makeText(SignupActivity.this, "Error in Sign Up Details, Check all the fields are filled", Toast.LENGTH_LONG).show();
        }


        }


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
                                Log.d("user",""+user);
                                user.setImageUrl(url.toString());

                                Picasso.with(SignupActivity.this).load(url.toString()).into(imageView);
                            }
                        });
            }
        }
    }






}

