package com.example.mcagataybarin.androquiz;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;

import com.example.mcagataybarin.androquiz.Fragments.BlankFragment;
import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;
import com.example.mcagataybarin.androquiz.Fragments.EditProfileFragment;
import com.example.mcagataybarin.androquiz.Fragments.FriendsFragment;
import com.example.mcagataybarin.androquiz.Fragments.ListFragment;
import com.example.mcagataybarin.androquiz.Fragments.QuestionFragment;
import com.example.mcagataybarin.androquiz.Fragments.RankingFragment;
import com.example.mcagataybarin.androquiz.Models.Category;
import com.example.mcagataybarin.androquiz.Models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DrawerActivity extends AppCompatActivity implements MemoGameFragment.OnFragmentInteractionListener, EndGameFragment.OnFragmentInteractionListener {


    private boolean friendsFragment = false;
    private FriendsFragment frag2;
    private RankingFragment frag3;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private String[] titles;
    private ListView drawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        System.out.println("INTERNET CONNECTION? ::   " + isNetworkAvailable());

        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Populate the ListView
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //lecture11oncreate
        //Display the correct fragment.
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }

        //Create the ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            //Called when a drawer has settled in a completely closed state
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            //Called when a drawer has settled in a completely open state.
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        FirebaseFunctions.getInstance().retrieveUsers(new Runnable() {
            public void run() {

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        currentPosition = position;
        BlankFragment fragment4 = null;
        final CategoryFragment[] fragment3 = {null};
        final MemoGameFragment[] fragment2 = {null};
        FriendsFragment frag = null;


        final Fragment[] fragment = new Fragment[1];
        friendsFragment = false;
        switch (position) {
            case 0:
                final DialogFragment newFragment = new LoadFragment();
                newFragment.show(getFragmentManager(), "loader");
                FirebaseFunctions.getInstance().getCategoryQuestions(new Runnable() {
                    @Override
                    public void run() {
                        QuestionData.getInstance().initialize(); // initialize game.
                        fragment3[0] = new CategoryFragment();
                        FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                        ft3.replace(R.id.content_frame, fragment3[0]);
                        ft3.addToBackStack(null);
                        ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft3.commitAllowingStateLoss();
                        newFragment.dismiss();
                    }
                });


                break;
            case 1:

                fragment2[0] = MemoGameFragment.newInstance(1);
                MemoData.getInstance().initialize();
                final android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                getFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.content_frame, fragment2[0]);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //fragment2.isLarge = true;
                        if(FirebaseFunctions.getInstance().rara != null) FirebaseFunctions.getInstance().rara.setVisibility(View.GONE);

                    }
                }, 6500);


                break;
            case 2:

                final DialogFragment newFragment55 = new LoadFragment();
                newFragment55.show(getFragmentManager(), "loader");

                FirebaseFunctions.getInstance().getUserById(new Runnable() {
                    public void run() {
                        fragment[0] = new EditProfileFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment[0]);
                        ft.addToBackStack(null);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        newFragment55.dismiss();
                        ft.commit();

                    }
                }, FirebaseAuth.getInstance().getCurrentUser().getUid());

                break;
            case 3:

                final DialogFragment newFragment2 = new LoadFragment();
                newFragment2.show(getFragmentManager(), "loader");
                friendsFragment = true;
                if (FirebaseFunctions.getInstance().all_users != null) {
                    System.out.println("şuan null değil ve burdayım ve size : " + FirebaseFunctions.getInstance().all_users2.size());

                    frag2 = new FriendsFragment();
                    FirebaseFunctions.getInstance().all_users = new ArrayList<FirebaseFunctions.UserID>();
                    System.out.println("Size : " + FirebaseFunctions.getInstance().all_users2.size());
                    FirebaseFunctions.getInstance().all_users2 = new ArrayList<>(new LinkedHashSet<>(FirebaseFunctions.getInstance().all_users2));
                    for (int i = 0; i < FirebaseFunctions.getInstance().all_users2.size(); i++) {
                        //frag2.requests.add(FirebaseFunctions.getInstance().all_users2.get(i));
                        if (FirebaseFunctions.getInstance().temp_user.user.friends != null) {
                            if (FirebaseFunctions.getInstance().temp_user.user.friends.contains(FirebaseFunctions.getInstance().all_users2.get(i).id)) {
                                frag2.requests.add(FirebaseFunctions.getInstance().all_users2.get(i));
                                System.out.println("EKLIOM AHU " + FirebaseFunctions.getInstance().all_users2.get(i).user);
                            }
                        }
                    }

                    frag2.requests = new ArrayList<>(new LinkedHashSet<>(frag2.requests));
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag2);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    newFragment2.dismiss();
                    ft.commit();

                } else {

                    FirebaseFunctions.getInstance().retrieveUsers(new Runnable() {
                        public void run() {

                            frag2 = new FriendsFragment();
                            FirebaseFunctions.getInstance().all_users2 = new ArrayList<>(new LinkedHashSet<>(FirebaseFunctions.getInstance().all_users2));

                            System.out.println("Size : " + FirebaseFunctions.getInstance().all_users2.size());

                            for (int i = 0; i < FirebaseFunctions.getInstance().all_users2.size(); i++) {
                                //frag2.requests.add(FirebaseFunctions.getInstance().all_users2.get(i));
                                if (FirebaseFunctions.getInstance().temp_user.user.friends != null) {
                                    if (FirebaseFunctions.getInstance().temp_user.user.friends.contains(FirebaseFunctions.getInstance().all_users2.get(i).id)) {

                                        if (!frag2.requests.contains(FirebaseFunctions.getInstance().all_users2.get(i))) {
                                            frag2.requests.add(FirebaseFunctions.getInstance().all_users2.get(i));
                                            System.out.println("EKLIOM AHU 2 " + FirebaseFunctions.getInstance().all_users2.get(i).user);
                                        }
                                    }
                                }
                            }

                            frag2.requests = new ArrayList<>(new LinkedHashSet<>(frag2.requests));
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag2);
                            ft.addToBackStack(null);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.commit();

                            newFragment2.dismiss();
                        }
                    });

                }
                break;
            //friends
            case 4:
                final DialogFragment newFragment3 = new LoadFragment();
                newFragment3.show(getFragmentManager(), "loader");
                FirebaseFunctions.getInstance().getScores(new Runnable() {
                    public void run() {
                        frag3 = new RankingFragment();
                        if (frag3.my_list != null) frag3.my_list.clear();
                        for (int i = 0; i < FirebaseFunctions.getInstance().all_scores2.size(); i++) {
                            frag3.requests.add(FirebaseFunctions.getInstance().all_scores2.get(i));
                        }
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag3);
                        ft.addToBackStack(null);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();

                        newFragment3.dismiss();
                    }
                });
                break;
            default:
                fragment4 = new BlankFragment();
                FragmentTransaction ft4 = getFragmentManager().beginTransaction();
                ft4.replace(R.id.content_frame, fragment4);
                ft4.addToBackStack(null);
                ft4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft4.commit();
                //fragment3.isLarge = true;
        }

        //Set the action bar title
        setActionBarTitle(position);
        //Close drawer
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        friendsFragment = false;
        invalidateOptionsMenu();
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //setIntent("This is example text");

        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    public void restartGame(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
