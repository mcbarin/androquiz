package com.example.mcagataybarin.androquiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class EndGameFragment extends Fragment {

    private static final String ARG_PARAM1 = "game"; // quiz or memo

    private String mGame;

    private OnFragmentInteractionListener mListener;

    public EndGameFragment() {
        // Required empty public constructor
    }


    public static EndGameFragment newInstance(String game) {
        EndGameFragment fragment = new EndGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGame = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_end_game, container, false);

        String title_text = "Hello ";
        String score_text = "Your Score: ";

        if (mGame.equals("quiz")){
            // Quiz game.
            title_text += QuestionData.getInstance().getUser().username + "!";
            score_text += ""+QuestionData.getInstance().getPoint();
        }else {
            title_text += MemoData.getInstance().getCurrentUser().username + "!";
            score_text += ""+MemoData.getInstance().score.getScore();
        }

        TextView titleView = (TextView) view.findViewById(R.id.endGameTitle);
        TextView scoreView = (TextView) view.findViewById(R.id.endGameScore);

        titleView.setText(title_text);
        scoreView.setText(score_text);

        // Set on click listener for restart button.
        Button restartGame = (Button) view.findViewById(R.id.endRestartButton);
        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

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
        void onFragmentInteraction(Uri uri);
    }
}
