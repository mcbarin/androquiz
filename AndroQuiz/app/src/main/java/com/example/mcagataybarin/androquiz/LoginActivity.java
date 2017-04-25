package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




// https://firebase.google.com/docs/auth/android/manage-users#update_a_users_profile

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = LoginActivity.class.getSimpleName();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // For Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String user_id = user.getUid();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user_id);
                    FirebaseFunctions.getInstance().user_id = user_id;


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Bienvenidos!", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Oooppss! Try Again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            LoginActivity.this.startActivity(intent);

                        }
                    }
                });
    }


    public void onClickLogin(View view){
        final EditText email = (EditText) findViewById(R.id.emailField);
        final EditText password = (EditText) findViewById(R.id.passwordField);

        if(email.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter e-mail.", Toast.LENGTH_SHORT).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
        } else {
            signIn(email.getText().toString(), password.getText().toString());
        }



    }

    /*
    * Starts RegisterActivity for user to sign up an account.
    * */
    public void toRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }

}
