package com.example.mcagataybarin.androquiz.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.FirebaseFunctions;
import com.example.mcagataybarin.androquiz.Models.User;
import com.example.mcagataybarin.androquiz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class FriendsFragment extends Fragment implements View.OnClickListener {

    View v;
    private EditText username, name, surname, city;
    private Button save;
    private User temp;

    private ArrayList<User> friends = new ArrayList<>();
    private DatabaseReference mDatabase;
    private boolean hasNotif;
    public ArrayList<FirebaseFunctions.UserID> requests = new ArrayList<>();
    public MyListAdaper my_list;
    private View view;
    private TextView notif;
    private boolean aradi  = false;

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        aradi = false;
        MenuItem item = menu.findItem(R.id.menuSearch);
        item.setVisible(true);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                aradi = true;
                System.out.println("query : " + query);

                ArrayList<FirebaseFunctions.UserID> arl = new ArrayList<FirebaseFunctions.UserID>();

                for(int i =0;i<FirebaseFunctions.getInstance().all_users2.size();i++){
                    arl.add(FirebaseFunctions.getInstance().all_users2.get(i));
                }
                my_list.clear();
                for(int i = 0; i<arl.size();i++){
                    FirebaseFunctions.UserID temp = arl.get(i);

                    if(temp.user.username.toLowerCase().contains(query.toLowerCase()) ||
                            temp.user.surname.toLowerCase().contains(query.toLowerCase()) ||
                            temp.user.name.toLowerCase().contains(query.toLowerCase())){
                        //Matched
                        System.out.println("şuanki uzer match: "+ temp.user);
                        requests.add(temp);
                        my_list.notifyDataSetChanged();

                    }else {
                        requests.remove(temp);
                        my_list.notifyDataSetChanged();
                    }
                }
                System.out.println("YOO : " + requests.size());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friends_fragment, container, false);
        notif = (TextView) view.findViewById(R.id.notif);


        ListView lv = (ListView) view.findViewById(R.id.listview);

        my_list = new MyListAdaper(getActivity(), R.layout.friends_user, requests);
        lv.setAdapter(my_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("List item was clicked at " + position);
            }
        });

        return view;
    }

    public class MyListAdaper extends ArrayAdapter<FirebaseFunctions.UserID> {
        private int layout;
        private ArrayList<FirebaseFunctions.UserID> mObjects;
        public MyListAdaper(Context context, int resource, ArrayList<FirebaseFunctions.UserID> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                viewHolder.button2 = (Button) convertView.findViewById(R.id.list_item_btn2);

                if(aradi){ viewHolder.button2.setVisibility(View.INVISIBLE); viewHolder.button.setText("Add Friend");}

                viewHolder.title.setText(getItem(position).user.username + " " + getItem(position).user.name);

                convertView.setTag(viewHolder);
            }


            mainViewholder = (ViewHolder) convertView.getTag();

            if(aradi){ mainViewholder.button2.setVisibility(View.INVISIBLE); mainViewholder.button.setText("Add Friend");}

            mainViewholder.title.setText(getItem(position).user.username + " " + getItem(position).user.name);
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    v.startAnimation(shake);
                    User temp = getItem(position).user;
                    String user_id = getItem(position).id;

                    if(aradi) {
                        if (temp.requests == null) {
                            temp.requests = new ArrayList<String>();
                        }

                        temp.requests.add(user_id);
                        FirebaseFunctions.getInstance().postUserDirect(temp);
                    }


                }
            });

            mainViewholder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    v.startAnimation(shake);
                    User temp = getItem(position).user;
                    String user_id = getItem(position).id;
                    temp.requests.remove(user_id);
                    FirebaseFunctions.getInstance().postUserDirect(temp);

                    requests.remove(position);
                    my_list.clear();
                    my_list.notifyDataSetChanged();


                    if(!hasNotif) notif.setVisibility(View.VISIBLE);

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("NEREYE BASTI BU AHU");


                }
            });

            return convertView;
        }
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
        Button button2;
    }

    public void retrieveUserFriends(final Runnable onLoaded) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("users").equalTo(FirebaseFunctions.getInstance().getCurrentUserId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        User event = issue.getValue(User.class);
//                        Log.d("Event ", event.city + " " + event.movie + " " + event.event_id);
                        friends.add(event);
                    }
                    onLoaded.run();
                }else{
                    notif.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        setHasOptionsMenu(true);


    }

    @Override
    public void onClick(View v) {

    }
}