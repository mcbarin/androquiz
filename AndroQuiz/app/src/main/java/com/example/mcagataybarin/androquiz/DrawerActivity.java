package com.example.mcagataybarin.androquiz;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;
import com.example.mcagataybarin.androquiz.Fragments.EditProfileFragment;
import com.example.mcagataybarin.androquiz.Fragments.FriendsFragment;
import com.example.mcagataybarin.androquiz.Fragments.ListFragment;
import com.example.mcagataybarin.androquiz.Fragments.QuestionFragment;
import com.example.mcagataybarin.androquiz.Models.Category;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity implements MemoGameFragment.OnFragmentInteractionListener, EndGameFragment.OnFragmentInteractionListener {


    private boolean friendsFragment = false;
    private FriendsFragment frag2;

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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        currentPosition = position;
        CategoryFragment fragment3 = null;
        MemoGameFragment fragment2 = null;
        FriendsFragment frag = null;
        final Fragment[] fragment = new Fragment[1];
        friendsFragment = false;
        switch (position) {
            case 1:
                fragment2 = MemoGameFragment.newInstance(1);
                MemoData.getInstance().initialize();
                //fragment2.isLarge = true;

                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                getFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.content_frame, fragment2);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
                break;
            case 2:

                final DialogFragment newFragment = new LoadFragment();
                newFragment.show(getFragmentManager(), "loader");

                if (FirebaseFunctions.getInstance().temp_user != null) {
                    fragment[0] = new EditProfileFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment[0]);
                    ft.addToBackStack(null);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    newFragment.dismiss();
                    ft.commit();
                } else {
                    FirebaseFunctions.getInstance().getUserById(new Runnable() {
                        public void run() {
                            fragment[0] = new EditProfileFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment[0]);
                            ft.addToBackStack(null);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            newFragment.dismiss();
                            ft.commit();

                        }
                    }, FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
                break;
            case 3:
                friendsFragment=true;
                frag2 = new FriendsFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, frag2);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            //friends
            case 4:
                //rankings
            default:
                QuestionData.getInstance().initialize(); // initialize game.
                fragment3 = new CategoryFragment();
                FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                ft3.replace(R.id.content_frame, fragment3);
                ft3.addToBackStack(null);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft3.commit();
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
    public void onBackPressed(){
        friendsFragment = false;
        invalidateOptionsMenu();
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        MenuItem item = menu.findItem(R.id.menuSearch);

        if (friendsFragment) item.setVisible(true);

        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("query : " + query);

                FriendsFragment.Request re = new FriendsFragment.Request(FirebaseFunctions.getInstance().getCurrentUserId(),
                        FirebaseFunctions.getInstance().temp_user);

                frag2.requests.add(re);
                frag2.my_list.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
