/*
InClass 11

Name : Jagadish Ballur Ravi
ID:800935988

Name: Aninditha Madishetty
ID: 800936606
 */

package com.example.inclass11;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText mEmail;
    private EditText mPassword;
    private Button mlogin, msignup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Chat Room");

        mEmail = (EditText) findViewById(R.id.editTextName);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mlogin = (Button) findViewById(R.id.buttonLogin);
        msignup= (Button) findViewById(R.id.buttonSignup);

        mAuth = FirebaseAuth.getInstance();

        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                    Log.d("1","listner1");
                    finish();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(email.equals("")){
                    Toast.makeText(MainActivity.this,"Email is null",Toast.LENGTH_LONG).show();
                } else if(password.equals("")){
                    Toast.makeText(MainActivity.this,"Enter a password",Toast.LENGTH_LONG).show();
                } else{
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "SignInWithEmail:onComplete:" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "Incorrect Email or Password",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                        startActivity(intent);
                                        Log.d("1","listner");
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthlistener);
    }
}
