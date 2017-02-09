package com.example.vivek.homework7part1;


//Vivek Ravi
//group- 37 homework 7

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    String fname,lname,emailid,profile;

    EditText email;
    EditText password;
User user;
    private final static int SIGNUP_REQ_CODE = 101;

    Button btnLogin,btnSignUp;

    public static final String USER_OBJ="USEROBJ";
    public static final String TOKEN_STR="TOKEN";
    public static final String CHATAPP="CHATAPP";

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String userId ;

    CallbackManager callbackManager;
    LoginButton loginButton;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_main);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.editTextEmail);
        password=(EditText)findViewById(R.id.editTextPassword);

        btnLogin=(Button)findViewById(R.id.buttonlogin);
        btnSignUp=(Button)findViewById(R.id.buttonSignup);

        loginButton = (LoginButton) findViewById(R.id.login_button_fb);
        loginButton.setReadPermissions("email", "public_profile");



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.google_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlesignin();
            }
        });







        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("demo","access token" + loginResult.getAccessToken().getUserId());
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
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
                // [START_EXCLUDE]
                // updateUI(user);
                // [END_EXCLUDE]
            }
        };


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, SignupActivity.class);
                startActivityForResult(start,SIGNUP_REQ_CODE);

            }
        });

    }


    private void googlesignin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void loginAction(View view) {

        if(email.equals("")){
            Toast.makeText(MainActivity.this,"Email is null",Toast.LENGTH_LONG).show();
        } else if(password.equals("")){
            Toast.makeText(MainActivity.this,"Enter a password",Toast.LENGTH_LONG).show();
        }else if(email.getText().toString().length()>0&&password.getText().toString().length()>0)
        {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("demo", "signInWithEmail:onComplete:" + task.isSuccessful());


                            if (!task.isSuccessful()) {
                                Log.w("demo", "signInWithEmail:failed", task.getException());
                                Toast.makeText(getApplicationContext(), "Login was not successfull", Toast.LENGTH_SHORT).show();
                            }

                         Log.d("demo","usrrr id ==== "+ task.getResult().getUser().getUid());

                            userId = mAuth.getCurrentUser().getUid();
                            Intent loginIntent = new Intent(MainActivity.this,ChatActivity.class);
                            loginIntent.putExtra("userid", userId);
                            startActivity(loginIntent);

                        }
                    });

        }
        else
        {
            Toast.makeText(MainActivity.this,"Enter the login credentials",Toast.LENGTH_LONG).show();
        }




    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("demo", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "signInWithCredential:onComplete: fb:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("demo", "signInWithCredential fb:", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                        fname=task.getResult().getUser().getDisplayName();
                        lname="";
                        emailid=task.getResult().getUser().getEmail();
                        profile=task.getResult().getUser().getPhotoUrl().toString();


                            final String userid=mAuth.getCurrentUser().getUid();
                            user=new User(fname,lname,emailid,"unknown","unknown",profile,userid);
                            final int i;
                            //new User(acct.getGivenName(),acct.getFamilyName(),acct.getEmail(),acct.getPhotoUrl());
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("user"))
                                    {
                                        DataSnapshot d=dataSnapshot.child("user");
                                        if(d.hasChild(userid))
                                        {
                                            Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                                            intent.putExtra("userID",mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                        }
                                        else {
                                            mDatabase.child("user").child(userid).setValue(user);
                                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                            intent.putExtra("userID", mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                        }
                                    }
                                    else {
                                        mDatabase.child("user").child(userid).setValue(user);
                                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                        intent.putExtra("userID", mAuth.getCurrentUser().getUid());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });






                        }
                        // ...
                    }
                });}


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNUP_REQ_CODE) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "USER HAS BEEN CREATED", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getApplicationContext(), "ACCOUNT WAS NOT CREATED, SELECT A DIFFERENT MAIL ID", Toast.LENGTH_SHORT).show();

            }
        }


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        }



    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("demo", "firebaseAuthWithGoogle:" + acct.getId());
        fname=acct.getGivenName();
        lname=acct.getFamilyName();
        emailid=acct.getEmail();
        profile=acct.getPhotoUrl().toString();

        // profile=acct.getPhotoUrl();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("demo", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            final String userid=mAuth.getCurrentUser().getUid();
                            user=new User(fname,lname,emailid,"unknown","unknown",profile,userid);
                            final int i;
                            //new User(acct.getGivenName(),acct.getFamilyName(),acct.getEmail(),acct.getPhotoUrl());
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("user"))
                                    {
                                        DataSnapshot d=dataSnapshot.child("user");
                                        if(d.hasChild(userid))
                                        {
                                            Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                                            intent.putExtra("userID",mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                        }
                                        else {
                                            mDatabase.child("user").child(userid).setValue(user);
                                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                            intent.putExtra("userID", mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                        }
                                    }
                                    else {
                                        mDatabase.child("user").child(userid).setValue(user);
                                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                        intent.putExtra("userID", mAuth.getCurrentUser().getUid());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });






                        }
                        // ...
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
