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
import com.example.mcagataybarin.androquiz.Models.GameState;
import com.example.mcagataybarin.androquiz.Models.HighScore;
import com.example.mcagataybarin.androquiz.Models.User;
import com.example.mcagataybarin.androquiz.QuestionActivity;
import com.example.mcagataybarin.androquiz.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RankingFragment extends Fragment {

    View v;
    private EditText username, name, surname, city;
    private Button save;
    private User temp;

    private DatabaseReference mDatabase;
    private boolean hasNotif;
    public ArrayList<HighScore> requests = new ArrayList<>();
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

    public class MyListAdaper extends ArrayAdapter<HighScore> {
        private int layout;
        private ArrayList<HighScore> mObjects;

        public MyListAdaper(Context context, int resource, ArrayList<HighScore> objects) {
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

                viewHolder.title.setText(getItem(position).username + " Scored : " + getItem(position).score);

                convertView.setTag(viewHolder);
            }


            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.title.setText(getItem(position).username + " Scored : " + getItem(position).score);

            mainViewholder.button2.setVisibility(View.INVISIBLE);
            mainViewholder.button.setVisibility(View.INVISIBLE);



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