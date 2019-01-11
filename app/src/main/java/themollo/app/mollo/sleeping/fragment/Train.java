package themollo.app.mollo.sleeping.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import themollo.app.mollo.R;
import themollo.app.mollo.survey.FragmentLifeCycle;

/**
 * A simple {@link Fragment} subclass.
 */
public class Train extends Fragment implements FragmentLifeCycle{


    public Train() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_train, container, false);
    }

    @Override
    public void onResumeFragment(Context context) {

    }

    @Override
    public void onPauseFragment(Context context) {

    }
}
