package com.example.mcagataybarin.androquiz;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoGameFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mLevel";

    // TODO: Rename and change types of parameters
    private int mLevel; // 1,2 or 3
    private GridView gridView;
    private GridViewAdapter gridAdapter;

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

        View view = inflater.inflate(R.layout.fragment_memo_game, container, false);

        String[] flagNames = MemoData.getInstance().getFlagNames(25);

        LinearLayout target_flags = (LinearLayout) view.findViewById(R.id.target_flags_images);

        for(int i=0;i<5;i++){
            ImageView image = new ImageView(view.getContext());
            image.setImageBitmap(ImageViaAssets("img0.png"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10,0,10,0);
            target_flags.addView(image, layoutParams);
        }

        LinearLayout heart_images = (LinearLayout) view.findViewById(R.id.heart_images);
        int remaining_lives = MemoData.getInstance().lifePoint.getRemainingLife();
        for(int i=0;i<remaining_lives;i++){
            ImageView heart = new ImageView(view.getContext());
            heart.setBackground(getResources().getDrawable(R.drawable.heart));
            heart_images.addView(heart);
        }

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setNumColumns(5);
        gridAdapter = new GridViewAdapter(view.getContext(), R.layout.grid_item_layout, flagNames);
        gridView.setAdapter(gridAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
}
