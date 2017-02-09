package com.example.vivek.firebasestart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText name;
    EditText email;
    EditText pwd;

    String ename;
    String mail;
    String passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        assignView();

        mAuth = FirebaseAuth.getInstance();


    }

    private void assignView() {

         name = (EditText)findViewById(R.id.editTextfullname1);
         email = (EditText)findViewById(R.id.editTextemailaddress);
         pwd = (EditText)findViewById(R.id.editText3pass);

    }

    public void signUpAction(View view) {

        ename = name.getText().toString();
        mail = email.getText().toString();
        passwd = pwd.getText().toString();

        mAuth.createUserWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {

                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(), "new user created", Toast.LENGTH_SHORT).show();
                            User user = new User(ename, mail, passwd);

                            Intent start = new Intent(SignUpActivity.this, MainActivity.class);
                           // start.putExtra("", );
                            startActivity(start);


                            finish();

                        }


                    }
                });


    }

    public void cancelaction(View view) {
Intent intentlogin = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intentlogin);

    }
}
