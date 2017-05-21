package com.example.mcagataybarin.androquiz.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.FirebaseFunctions;
import com.example.mcagataybarin.androquiz.MemoData;
import com.example.mcagataybarin.androquiz.MemoGameActivity;
import com.example.mcagataybarin.androquiz.Models.GameState;
import com.example.mcagataybarin.androquiz.Models.User;
import com.example.mcagataybarin.androquiz.QuestionActivity;
import com.example.mcagataybarin.androquiz.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChallengeFragment extends Fragment {

    View v;
    private EditText username, name, surname, city;
    private Button save;
    private User temp;

    private DatabaseReference mDatabase;
    private boolean hasNotif;
    public ArrayList<FirebaseFunctions.gsID> requests = new ArrayList<>();
    public MyListAdaper my_list;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.challenge_fragment, container, false);

        ListView lv = (ListView) view.findViewById(R.id.listview2);

        my_list = new MyListAdaper(getActivity(), R.layout.challenge_user, requests);
        lv.setAdapter(my_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("List item was clicked at " + position);
            }
        });

        return view;
    }

    public class MyListAdaper extends ArrayAdapter<FirebaseFunctions.gsID> {
        private int layout;
        private ArrayList<FirebaseFunctions.gsID> mObjects;

        public MyListAdaper(Context context, int resource, ArrayList<FirebaseFunctions.gsID> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnaila);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_texta);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btna);
                viewHolder.button2 = (Button) convertView.findViewById(R.id.list_item_btn2a);

                ArrayList<FirebaseFunctions.UserID> rr = (ArrayList<FirebaseFunctions.UserID>) FirebaseFunctions.getInstance().all_users2.clone();

                String opponent = "";
                String creator =  "";

                for(int i = 0;i<rr.size();i++){
                    FirebaseFunctions.UserID temp = rr.get(i);
                    if(temp.id.equalsIgnoreCase(getItem(position).gs.opponent)){
                        opponent = temp.user.name + " " + temp.user.surname;
                    }
                    if(temp.id.equalsIgnoreCase(getItem(position).gs.creator)){
                        creator = temp.user.name + " " + temp.user.surname;
                    }
                }


                if(getItem(position).gs.creator.equalsIgnoreCase(FirebaseFunctions.getInstance().temp_user.id)){
                    viewHolder.title.setText("You challenged " + opponent);

                    if(getItem(position).gs.completed){
                        String txt = "";
                        viewHolder.button.setVisibility(View.INVISIBLE);
                        if(getItem(position).gs.score1 > getItem(position).gs.score2){
                            txt = "YOU WON THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2 +
                                    "against " + opponent;
                        }else{
                            txt = "YOU LOST THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2 +
                                    "against " + opponent;
                        }
                        viewHolder.title.setText(txt);
                    }


                }else if(getItem(position).gs.opponent.equalsIgnoreCase(FirebaseFunctions.getInstance().temp_user.id)){
                    viewHolder.title.setText(creator + "\n Challenged you");
                    FirebaseFunctions.getInstance().challenged = true;
                    if(getItem(position).gs.completed){
                        String txt = "";
                        viewHolder.button.setVisibility(View.INVISIBLE);
                        if(getItem(position).gs.score1 > getItem(position).gs.score2){
                            txt = "YOU LOST THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2+
                                    "against " + creator;
                        }else{
                            txt = "YOU WON THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2 +
                                    "against " + creator;
                        }
                        viewHolder.title.setText(txt);
                    }

                }

                if(!FirebaseFunctions.getInstance().challenged){
                    viewHolder.button.setVisibility(View.INVISIBLE);
                }

                convertView.setTag(viewHolder);
            }


            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.button2.setVisibility(View.INVISIBLE);
            mainViewholder.button.setText("Accept Challenge");

            ArrayList<FirebaseFunctions.UserID> rr = (ArrayList<FirebaseFunctions.UserID>) FirebaseFunctions.getInstance().all_users2.clone();

            String opponent = "";
            String creator =  "";

            for(int i = 0;i<rr.size();i++){
                FirebaseFunctions.UserID temp = rr.get(i);
                if(temp.id.equalsIgnoreCase(getItem(position).gs.opponent)){
                    opponent = temp.user.name + " " + temp.user.surname;
                }
                if(temp.id.equalsIgnoreCase(getItem(position).gs.creator)){
                    creator = temp.user.name + " " + temp.user.surname;
                }
            }

            String text;
            if(getItem(position).gs.creator.equalsIgnoreCase(FirebaseFunctions.getInstance().temp_user.id)){
                mainViewholder.title.setText("You challenged " + opponent);

                if(getItem(position).gs.completed){
                    String txt = "";
                    mainViewholder.button.setVisibility(View.INVISIBLE);
                    if(getItem(position).gs.score1 > getItem(position).gs.score2){
                        txt = "YOU WON THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2 +
                        "against " + opponent;
                    }else{
                        txt = "YOU LOST THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2 +
                                "against " + opponent;
                    }
                    mainViewholder.title.setText(txt);
                }


            }else if(getItem(position).gs.opponent.equalsIgnoreCase(FirebaseFunctions.getInstance().temp_user.id)){
                mainViewholder.title.setText(creator + "\n Challenged you");
                FirebaseFunctions.getInstance().challenged = true;
                if(getItem(position).gs.completed){
                    String txt = "";
                    mainViewholder.button.setVisibility(View.INVISIBLE);
                    if(getItem(position).gs.score1 > getItem(position).gs.score2){
                        txt = "YOU LOST THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2+
                                "against " + creator;
                    }else{
                        txt = "YOU WON THE CHALLENGE \n" + getItem(position).gs.score1 +" to" + " " + getItem(position).gs.score2+
                                "against " + creator;
                    }
                    mainViewholder.title.setText(txt);
                }

            }

            if(!FirebaseFunctions.getInstance().challenged){
                mainViewholder.button.setVisibility(View.INVISIBLE);
            }

            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    v.startAnimation(shake);

                    FirebaseFunctions.getInstance().curgs = getItem(position);

                    if(FirebaseFunctions.getInstance().curgs.gs.gameType == 0) {
                        Intent intent = new Intent();
                        intent = new Intent(getActivity(), QuestionActivity.class);
                        intent.putExtra("category", 10);
                        intent.putExtra("question", FirebaseFunctions.getInstance().quNum);
                        startActivity(intent);
                    }else{
                        FirebaseFunctions.getInstance().memochal = true;
                        MemoData.getInstance().initialize();
                        Intent intent = new Intent(getActivity(), MemoGameActivity.class);
                        startActivity(intent);
                    }

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

}