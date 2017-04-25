package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcagataybarin.androquiz.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private String email_s = "";
    private String username_s = "";
    private String name_s = "";
    private String surname_s = "";
    private String city_s = "";

    String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getCurrentWeek();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    String user_id = user.getUid();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user_id);
                    FirebaseFunctions.getInstance().user_id = user_id;
//                    FirebaseFunctions.getInstance().user_pp_url = user.getPhotoUrl().toString();
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


    public void onClickRegister(View view) {
        EditText username = (EditText) findViewById(R.id.nameField);
        EditText email = (EditText) findViewById(R.id.emailRegister);
        EditText city = (EditText) findViewById(R.id.cityUser);
        EditText name = (EditText) findViewById(R.id.nameUser);
        EditText surname = (EditText) findViewById(R.id.surnameUser);
        final EditText password = (EditText) findViewById(R.id.passwordRegister);
        username_s = username.getText().toString();
        email_s = email.getText().toString();
        city_s = city.getText().toString();
        name_s = name.getText().toString();
        surname_s = surname.getText().toString();

        createUserWithEmailAndPassword(username_s, email_s, city_s, name_s, surname_s, password.getText().toString());

        mAuth.signInWithEmailAndPassword(email_s, password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Bienvenidos!", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(RegisterActivity.this, "Oooppss! Try Again.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(RegisterActivity.this, ListActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        }
                    }
                });
    }

    public void getCurrentWeek() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference movies_reference = mDatabase.child("movies");

        movies_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseFunctions.getInstance().currentWeek = String.valueOf(dataSnapshot.getChildrenCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void createUserWithEmailAndPassword(String username_s, String email_s, String name_s, String surname_s, String city_s, String password) {
        this.username_s = username_s;
        this.name_s = name_s;
        this.email_s = email_s;
        this.surname_s = surname_s;
        this.city_s = city_s;
        mAuth.createUserWithEmailAndPassword(email_s, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Failed to create an account.", Toast.LENGTH_SHORT).show();
                        }

                        // User created. Do something.
                        FirebaseUser user = task.getResult().getUser();
                        String UID = user.getUid();
                        User new_user = new User(RegisterActivity.this.username_s, RegisterActivity.this.name_s,
                                RegisterActivity.this.surname_s, RegisterActivity.this.city_s, RegisterActivity.this.email_s);
                        mDatabase.child("users").child(UID).setValue(new_user);
                        FirebaseFunctions.getInstance().user_id = UID;

                    }
                });
    }


}
