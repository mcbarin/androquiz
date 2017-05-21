package com.example.mcagataybarin.androquiz;

import android.app.DialogFragment;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.mcagataybarin.androquiz.Models.GameState;
import com.example.mcagataybarin.androquiz.Models.HighScore;
import com.example.mcagataybarin.androquiz.Models.Question;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class FirebaseFunctions {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected String currentWeek = "";
    protected String user_id;
    protected String user_pp_url = "";
    private static final FirebaseFunctions ourInstance = new FirebaseFunctions();
    public UserID temp_user;
    private TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
    private Task dbTask = dbSource.getTask();
    public int quNum = 0;
    public String cur_opponent;
    public boolean challenged = false;
    public gsID curgs;
    public int level = 70;
    public DialogFragment df;
    public com.wang.avi.AVLoadingIndicatorView rara;

    public ArrayList<gsID> all_challenges, all_challenges2;
    public ArrayList<UserID> all_users, all_users2;
    public ArrayList<HighScore> all_scores, all_scores2;
    public ArrayList<Question> temp_questions = new ArrayList<>();
    public boolean memochal = false;
    public int gamelevel;
    public SQLiteDatabase myDB;
    public boolean sqlVar = false;

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
                        User tmp = dataSnapshot.getValue(User.class);
                        temp_user = new UserID(user_id,tmp);
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
                    User tmp = dataSnapshot.getValue(User.class);
                    temp_user = new UserID(user_id,tmp);
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
                        if(temp.id.equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            FirebaseFunctions.getInstance().temp_user = temp;
                        }
                    }

                    if(all_users2== null) {
                        all_users2 = new ArrayList<UserID>();
                        for (int i = 0; i < all_users.size(); i++) {
                            all_users2.add(all_users.get(i));
                        }
                        all_users2 = new ArrayList<>(new LinkedHashSet<>(all_users2));
                    }
                    onLoaded.run();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void retrieveChallenges(final Runnable onLoaded) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("gamestate");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String id = issue.getKey();
                        GameState event = issue.getValue(GameState.class);
                        if(all_challenges == null) all_challenges = new ArrayList<gsID>();
                        all_challenges.add(new gsID(id,event));
//
                    }

                    if(all_challenges2== null) {
                        all_challenges2 = new ArrayList<>();
                        for (int i = 0; i < all_challenges.size(); i++) {
                            all_challenges2.add(all_challenges.get(i));
                        }
                        all_challenges2 = new ArrayList<>(new LinkedHashSet<>(all_challenges2));
                    }

                    onLoaded.run();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public ArrayList<Question> getAllQuestions(){

        ArrayList<Question> arrayList = new ArrayList<Question>();

        Cursor c = myDB.rawQuery("SELECT * from question",null);

        //date, detail, director, duration, genre, image, title;

        int date_index = c.getColumnIndex("question");
        int detail_index = c.getColumnIndex("choice0");
        int director_index = c.getColumnIndex("choice1");
        int duration_index = c.getColumnIndex("choice2");
        int genre_index = c.getColumnIndex("choice3");
        int title_index = c.getColumnIndex("answer");
        int image_index = c.getColumnIndex("point");

        c.moveToFirst();
        int a = 1;
        int count = c.getCount();

        if(count == 0){
            return arrayList;
        }

        if(count == 1){
            Question temp = new Question();
            temp.question = c.getString(date_index);
            if(temp.choices == null) temp.choices = new ArrayList<>();
            temp.choices.add(c.getString(detail_index));
            temp.choices.add(c.getString(director_index));
            temp.choices.add(c.getString(duration_index));
            temp.choices.add(c.getString(genre_index));
            temp.answer = c.getInt(title_index);
            temp.point = c.getInt(image_index);
            arrayList.add(temp);

        } else {
            while (c != null) {
                a++;
                Question temp = new Question();
                temp.question = c.getString(date_index);
                if(temp.choices == null) temp.choices = new ArrayList<>();
                temp.choices.add(c.getString(detail_index));
                temp.choices.add(c.getString(director_index));
                temp.choices.add(c.getString(duration_index));
                temp.choices.add(c.getString(genre_index));
                temp.answer = c.getInt(title_index);
                temp.point = c.getInt(image_index);
                arrayList.add(temp);
                if (count != a) {
                    c.moveToNext();
                } else {
                    c.moveToNext();
                    Question temp2 = new Question();
                    temp2.question = c.getString(date_index);
                    if(temp.choices == null) temp.choices = new ArrayList<>();
                    temp2.choices.add(c.getString(detail_index));
                    temp2.choices.add(c.getString(director_index));
                    temp2.choices.add(c.getString(duration_index));
                    temp2.choices.add(c.getString(genre_index));
                    temp2.answer = c.getInt(title_index);
                    temp2.point = c.getInt(image_index);
                    arrayList.add(temp2);
                    break;
                }

            }
        }

        return arrayList;
    }


    public void addToDatabase(Question que) {
        //Add to SQLite, to the movie table.

        String question = que.question;
        String choice0 = que.choices.get(0);
        String choice1 = que.choices.get(1);
        String choice2 = que.choices.get(2);
        String choice3 = que.choices.get(3);
        int answer = que.answer;
        int point = que.point;
        question = question.replaceAll("'", " ");
        System.out.println(question);
        myDB.execSQL("INSERT INTO question (question, choice0, choice1, choice2, choice3, answer, point) VALUES " +
                "('" + question + "', '" + choice0 + "', '" + choice1 + "', '" +
                choice2 + "', '" + choice3 + "', '" + answer + "', '" + point + "' )");
        //End
    }


    public void getScores(final Runnable onLoaded) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("highscore").orderByChild("score");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        HighScore event = issue.getValue(HighScore.class);
                        if(all_scores == null) all_scores = new ArrayList<HighScore>();
                        all_scores.add(event);
//
                    }

                    if(all_scores2== null) {
                        all_scores2 = new ArrayList<>();
                        for (int i = 0; i < all_scores.size(); i++) {
                            all_scores2.add(all_scores.get(i));
                        }
                        all_scores2 = new ArrayList<>(new LinkedHashSet<>(all_scores2));
                        java.util.Collections.reverse(all_scores2);
                    }

                    onLoaded.run();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void getCategoryQuestions(final Runnable onLoaded){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Query query = mDatabase.child("quiz");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot issue: dataSnapshot.getChildren()) {
                        for (DataSnapshot questionData: issue.getChildren()) {
                            Question question = new Question(questionData);
                            temp_questions.add(question);
                            if(!sqlVar) {
                                addToDatabase(question);
                            }
                        }
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

    public static class gsID{

        public String id;
        public GameState gs;

        public gsID(String id, GameState gs){
            this.id = id;
            this.gs = gs;

        }
    }

    public void postUserDirect(User temp, String id) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(id).setValue(temp);
    }

    public void postGameState(GameState gs){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("gamestate").push().setValue(gs);
    }

    public void postGameStateDirect(String id, GameState gs){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("gamestate").child(id).setValue(gs);
    }

    public void postHighScore(HighScore hs){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("highscore").push().setValue(hs);
    }

}