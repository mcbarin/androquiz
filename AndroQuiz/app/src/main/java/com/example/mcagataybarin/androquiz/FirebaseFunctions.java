package com.example.mcagataybarin.androquiz;

import android.util.Log;
import android.view.View;

import com.example.mcagataybarin.androquiz.Models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class FirebaseFunctions {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected String currentWeek = "";
    protected String user_id;
    protected String user_pp_url = "";
    private static final FirebaseFunctions ourInstance = new FirebaseFunctions();
    public User temp_user;
    private TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
    private Task dbTask = dbSource.getTask();

    public ArrayList<UserID> all_users;

    public static FirebaseFunctions getInstance() {
        return ourInstance;
    }

    private FirebaseFunctions() {
    }



    public String getCurrentUserId() {
        return user_id;
    }

    public String getCurrentUserPp() {
        return user_pp_url;
    }

    public String get_random_id() {
        int length = 15;
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();

    }


    public String getCurrentWeek() {

        return currentWeek;
    }


    // Returns the user object by its id.
    public void getUserById(final Runnable onLoaded, String id) {

            this.user_id = id;
            mDatabase = FirebaseDatabase.getInstance().getReference();

            DatabaseReference ref = mDatabase.child("users").child(id);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        temp_user = dataSnapshot.getValue(User.class);
                    }
                    onLoaded.run();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

    }

    // Returns the user object by its id.
    public void getUserById(String id) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("users").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    temp_user = dataSnapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void retrieveUsers(final Runnable onLoaded) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String id = issue.getKey();

                        User event = issue.getValue(User.class);
//                        Log.d("Event ", event.city + " " + event.movie + " " + event.event_id);
                        UserID temp = new UserID(id, event);
                        if(all_users == null){
                            all_users = new ArrayList<UserID>();
                        }
                        all_users.add(temp);
                    }
                    onLoaded.run();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    public static class UserID{

        public String id;
        public User user;

        public UserID(String id, User user){
            this.id = id;
            this.user = user;

        }
    }

    public void postUserDirect(User temp) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(getCurrentUserId()).setValue(temp);
    }

}