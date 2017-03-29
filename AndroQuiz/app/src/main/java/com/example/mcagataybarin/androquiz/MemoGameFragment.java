package com.example.mcagataybarin.androquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoGameFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mLevel";

    // TODO: Rename and change types of parameters
    private int mLevel; // 1,2 or 3
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private View view;
    private LinearLayout heart_images;
    Resources res;


    // For handling the choosing flags.
    int lastOpenedFlagPosition;
    String lastOpenedFlagName;
    boolean isAnyFlagOpen = false;
    boolean isSuccessfull = false;
    int remainingTargetFlags;

    private OnFragmentInteractionListener mListener;

    public MemoGameFragment() {
        // Required empty public constructor
    }

    public static MemoGameFragment newInstance(int mLevel) {
        MemoGameFragment fragment = new MemoGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, mLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLevel = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        res = getContext().getResources();
        view = inflater.inflate(R.layout.fragment_memo_game, container, false);
        remainingTargetFlags = mLevel + 3;


        // First get the flag data from MemoData class.
        ArrayList<String> target_flagnames = MemoData.getInstance().getTargetFlags(mLevel);
        ArrayList<String> flag_list = MemoData.getInstance().getFlagList(mLevel);

        LinearLayout target_flags = (LinearLayout) view.findViewById(R.id.target_flags_images);

        for(int i=0; i<target_flagnames.size(); i++){
            ImageView image = new ImageView(view.getContext());
            image.setImageBitmap(ImageViaAssets(target_flagnames.get(i)));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10,10,10,10);
            target_flags.addView(image, layoutParams);
        }

        // For setting up the life images programmatically <3 <3 <3
        heart_images = (LinearLayout) view.findViewById(R.id.heart_images);
        final int remaining_lives = MemoData.getInstance().lifePoint.getRemainingLife();
        for(int i=0;i<remaining_lives;i++){
            ImageView heart = new ImageView(view.getContext());
            heart.setBackground(getResources().getDrawable(R.drawable.heart));
            heart_images.addView(heart);
        }

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setNumColumns(target_flagnames.size());
        gridView.setEnabled(false);
        gridAdapter = new GridViewAdapter(view.getContext(), R.layout.grid_item_layout, flag_list);
        gridView.setAdapter(gridAdapter);

        updateScore(view);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View local_view, final int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Log.i("ITEM", item);

                final ImageView image = getImageViewAtIndex(position);
                showFlag(image);

                if(isAnyFlagOpen){
                    // Then check if it's same or not.
                    boolean isSame = lastOpenedFlagName.equals(item);
                    if (isSame){
                        // Increment Point.
                        Log.i("STATUS", "RIGHT");
                        MemoData.getInstance().score.incrementScore();
                        updateScore(view);

                        isAnyFlagOpen = false;
                        isSuccessfull = true;

                        // Hide the flag from target flag list
                        removeFlagFromTargetList(item);

                        // Make both images unclickable.
                        MemoData.getInstance().setCellUnclickable(position);
                        MemoData.getInstance().setCellUnclickable(lastOpenedFlagPosition);

                        // Check if game is ended with success or not.
                        remainingTargetFlags -= 1;
                        if (remainingTargetFlags == 0){
                            // Game ended, go to next level. If this is the last level, show game score.
                            View fragmentContainer = getView().findViewById(R.id.fragment_container);

                            if (mLevel != 3){
                                // Check the fragment container for tablet or phone.
                                MemoData.getInstance().levelUp();
                                // Prepare the fragment.
                                MemoGameFragment fragment = MemoGameFragment.newInstance(mLevel + 1);

                                if (fragmentContainer != null){ // Tablet
                                    Log.i("PLACE", "TABLET");
                                    android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                    transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    transaction.replace(R.id.fragment_container, fragment).commit();
                                } else{ // Phone
                                    Log.i("PLACE", "PHONE");
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    transaction.replace(R.id.memo_fragment, fragment).commit();
                                }
                            } else{
                                // Level 3. Game is successfully finished.

                                EndGameFragment fragment = EndGameFragment.newInstance("memo");

                                if (fragmentContainer != null){
                                    android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                    transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    transaction.replace(R.id.fragment_container, fragment).commit();
                                } else{ // Phone
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    transaction.replace(R.id.memo_fragment, fragment).commit();
                                }
                            }
                        }
                    }else {
                        // Then it failed.
                        Log.i("STATUS", "WRONG");
                        isSuccessfull = false;
                        isAnyFlagOpen = false;
                        gridView.setEnabled(false); // disable the grid view from click.

                        decrementLifePoint();

                        final ImageView previousImage = getImageViewAtIndex(lastOpenedFlagPosition);

                        boolean isFailed = MemoData.getInstance().lifePoint.isFailed();
                        if (!isFailed) {
                            // Hide the flag after one second.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hideFlag(image);
                                    hideFlag(previousImage);
                                    gridView.setEnabled(true); // Enable the grid view again.
                                }
                            }, 2000);
                        }
                    }

                }else {
                    lastOpenedFlagName = item;
                    lastOpenedFlagPosition = position;
                    isAnyFlagOpen = true;
                    isSuccessfull = false;
                    if (image != null) {
                        showFlag(image);
                        // Show flag for 5 seconds.
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!MemoData.getInstance().isFlagMatched(position)) {
                                    hideFlag(image);
                                    if(isAnyFlagOpen)
                                        decrementLifePoint();
                                }
                                isAnyFlagOpen = false;

                            }
                        }, 5000);

                    }
                }

            }
        });

        showFlagsOnStart();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Returns a Bitmap object from assets folder with given file name argument.
    public Bitmap ImageViaAssets(String fileName){

        AssetManager assetmanager = getContext().getAssets();
        InputStream is = null;
        try{

            is = assetmanager.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void hideFlag(ImageView image){
        final int newColor = res.getColor(R.color.flag_unvisible);
        image.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
    }

    public void showFlag(ImageView image){
        image.setColorFilter(null);
    }

    private ImageView getImageViewAtIndex(int index){
        View image_view = gridView.getChildAt(index);

        if (image_view != null)
            return (ImageView) image_view.findViewById(R.id.grid_image);
        return null;
    }

    public void showFlagsOnStart(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // First show all images.
                for (int i=0; i< gridView.getChildCount(); i++){
                    View imageView = gridView.getChildAt(i);
                    if (imageView != null){
                        ImageView image = (ImageView) imageView.findViewById(R.id.grid_image);
                        hideFlag(image);
                    }
                }
                gridView.setEnabled(true);
            }
        }, 5000);

    }

    public void updateScore(View view){
        int current_score = MemoData.getInstance().score.getScore();
        TextView score = (TextView) view.findViewById(R.id.memo_score);
        score.setText("Score: " + ""+ current_score);
    }

    public void removeFlagFromTargetList(String flagName){
        LinearLayout targetFlags = (LinearLayout) view.findViewById(R.id.target_flags_images);
        ArrayList<String> targetFlagName = MemoData.getInstance().target_flags.get(mLevel-1);
        for (int i=0; i<targetFlagName.size(); i++){
            if (targetFlagName.get(i).equals(flagName)){
                View targetView = targetFlags.getChildAt(i);
                targetView.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }

    // Remove one life point of user.
    public void decrementLifePoint() {
        MemoData.getInstance().lifePoint.decrementRemainingLife();

        boolean isFailed = MemoData.getInstance().lifePoint.isFailed();
        if (isFailed) {
            // GAME OVER;
            gridView.setEnabled(false); // Disable the gridView.

            // Show score page.
            View fragmentContainer = getActivity().findViewById(R.id.fragment_container);
            EndGameFragment fragment = EndGameFragment.newInstance("memo");

            if (fragmentContainer != null) { //  Tablet
                Log.i("LIFE", "TABLET");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.fragment_container, fragment).commit();
            } else { // Phone
                Log.i("LIFE", "PHONE");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.memo_fragment, fragment).commit();
            }

        } else {

            heart_images.removeViewAt(0);
        }
    }
}
