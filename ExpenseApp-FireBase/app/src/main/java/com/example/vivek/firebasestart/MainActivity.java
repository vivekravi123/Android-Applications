package com.example.vivek.firebasestart;

/*
* Vivek Ravi
* Group 37 - inclass 10
* */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String emailStr;
    String pwdStr;

    EditText email;
    EditText pwd;

    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    ArrayList<Expenses> expensesList = new ArrayList<Expenses>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


    }

    private void assignViews() {

        email = (EditText) findViewById(R.id.email_Edit_login);
        pwd = (EditText) findViewById(R.id.pwd_edit_login);


    }


    public void CreateAccountAction(View view) {


        Intent startacc = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(startacc);

    }


    public void LoginAction1(View view) {
        emailStr = email.getText().toString();
        pwdStr = pwd.getText().toString();



        /*mAuth.createUserWithEmailAndPassword(emailStr, pwdStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {

                        }



                    }
                });*/


        mAuth.signInWithEmailAndPassword(emailStr, pwdStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "signInWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Log.w("demo", "signInWithEmail:failed", task.getException());
                        }


                    }
                });








        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("demo", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("demo", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        String userId = mAuth.getCurrentUser().getUid();


        Intent start = new Intent(MainActivity.this, ExpenseActivity.class);
        start.putParcelableArrayListExtra("AddExpense", expensesList);
        start.putExtra("userId", userId);
        startActivity(start);


    }
}
