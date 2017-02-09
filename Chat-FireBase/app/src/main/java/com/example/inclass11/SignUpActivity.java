package com.example.inclass11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button mCancel, mSignUp;
    EditText mEmail, mPassword, mFName, mLName, mRPass;
    String email, pass, rPass, fName, lName;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Sign Up");

        mEmail = (EditText) findViewById(R.id.signUPEmail);
        mPassword = (EditText) findViewById(R.id.signUPPassword);
        mRPass = (EditText) findViewById(R.id.signUPRepeatPassword);
        mFName = (EditText) findViewById(R.id.signUPFirstName);
        mLName = (EditText) findViewById(R.id.signUPLastName);
        mCancel = (Button) findViewById(R.id.buttonCancel);
        mSignUp = (Button) findViewById(R.id.signUp);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                pass = mPassword.getText().toString();
                rPass = mRPass.getText().toString();
                fName = mFName.getText().toString();
                lName = mLName.getText().toString();

                if(fName.equals("")){
                    Toast.makeText(SignUpActivity.this,"First Name is null",Toast.LENGTH_LONG).show();
                } else if(lName.equals("")){
                    Toast.makeText(SignUpActivity.this,"Last Name is null",Toast.LENGTH_LONG).show();
                } else if(email.equals("")){
                    Toast.makeText(SignUpActivity.this,"Email is null",Toast.LENGTH_LONG).show();
                } else if(pass.equals("")){
                    Toast.makeText(SignUpActivity.this,"Choose a password",Toast.LENGTH_LONG).show();
                } else if(!rPass.equals(pass)){
                    Toast.makeText(SignUpActivity.this,"Password did not match",Toast.LENGTH_LONG).show();
                } else{
                    mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                            if(task.isSuccessful()){
                                int size = task.getResult().getProviders().size();
                                if(size >=1)
                                    Toast.makeText(SignUpActivity.this, "Email alredy exists, try with different email", Toast.LENGTH_LONG).show();
                                else {
                                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                onAuthSuccess(task.getResult().getUser());
                                            } else {
                                                Toast.makeText(SignUpActivity.this, "Error Signing up", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = fName + " " + lName;
        writeNewUser(user.getUid(), username, user.getEmail());

        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }
}

