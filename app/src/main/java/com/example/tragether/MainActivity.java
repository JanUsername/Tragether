package com.example.tragether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSION_SIGN_IN = 1;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    SignInButton signInButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PERMISSION_SIGN_IN){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){

                GoogleSignInAccount account = result.getSignInAccount();
                String idToken = account.getIdToken();

                AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
                firebaseAuthWithGoogle(credential);

            }else{

                String msg = result.getStatus().getStatusMessage();

                Log.e("EDMT_ERROR", "Login failed");
                Log.e("EDMT_ERROR", msg);

            }

        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //start new activity and pass email to the new activity
                        Intent intent = new Intent(MainActivity.this, logged_activity.class);
                        //intent.putExtra("email", authResult.getUser().getEmail());
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(FirebaseAuth.getInstance() != null){

            mAuth = FirebaseAuth.getInstance();
            Intent intent = new Intent(MainActivity.this, logged_activity.class);
            intent.putExtra("username", mAuth.getCurrentUser().getEmail());
            startActivity(intent);
        }else {


            configureGoogleSignIn();
            signInButton = (SignInButton) findViewById(R.id.sign_in_button);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }

       // https://www.youtube.com/watch?v=4h4y4mnJIBs

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, PERMISSION_SIGN_IN);


    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        mGoogleApiClient.connect();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, ""+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }

    /*need later to move on if already logged in
    @Override
    public void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }*/



    // @SuppressWarnings("StatementWithEmptyBody")


}
