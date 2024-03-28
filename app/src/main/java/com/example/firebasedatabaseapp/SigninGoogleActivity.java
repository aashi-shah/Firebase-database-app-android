package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SigninGoogleActivity extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    SignInButton button;
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_google);
        button = findViewById(R.id.signinbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId("498196212694-r06kdn910jngjlvm5add0piduprphc07.apps.googleusercontent.com")
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("498196212694-r06kdn910jngjlvm5add0piduprphc07.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //token generate using google console and set client id

        googleSignInClient = GoogleSignIn.getClient(this,gso);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = googleSignInClient.getSignInIntent();
                startActivityForResult(i,1);

            }
        });
    }
    //return result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            // Google Sign In was successful, authenticate with Firebase
            //GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                firebaseGoogleLogin(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseGoogleLogin(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        Log.e("Google Login"," "+account);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent i = new Intent(SigninGoogleActivity.this,DisplayuserActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}