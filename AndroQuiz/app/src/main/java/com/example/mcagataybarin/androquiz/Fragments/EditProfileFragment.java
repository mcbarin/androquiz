package com.example.mcagataybarin.androquiz.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.FirebaseFunctions;
import com.example.mcagataybarin.androquiz.LoadFragment;
import com.example.mcagataybarin.androquiz.Models.User;
import com.example.mcagataybarin.androquiz.QuestionActivity;
import com.example.mcagataybarin.androquiz.QuestionData;
import com.example.mcagataybarin.androquiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import static android.app.Activity.RESULT_OK;

/**
 * Created by aea on 13/05/17.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    View v;
    private EditText username, name, surname, city;
    private Button save;
    private User temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_activity, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getView();

        save = (Button)  v.findViewById(R.id.save);
        save.setOnClickListener(this);
        FirebaseFunctions.getInstance().getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid());

        name = (EditText) v.findViewById(R.id.new_name);
        surname = (EditText) v.findViewById(R.id.new_surname);
        username = (EditText) v.findViewById(R.id.new_username);
        city = (EditText) v.findViewById(R.id.new_city);

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseFunctions.getInstance().getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(temp == null)) {
                    name.setText(temp.name);
                    surname.setText(temp.surname);
                    city.setText(temp.city);
                    username.setText(temp.username);

                }else{
                    temp = dataSnapshot.getValue(User.class);
                    name.setText(temp.name);
                    surname.setText(temp.surname);
                    city.setText(temp.city);
                    username.setText(temp.username);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        temp.name = name.getText().toString();
        temp.city = city.getText().toString();
        temp.surname = surname.getText().toString();
        temp.username = username.getText().toString();

        FirebaseFunctions.getInstance().postUserDirect(temp, FirebaseAuth.getInstance().getCurrentUser().getUid());
        getActivity().onBackPressed();

    }
}
