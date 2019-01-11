package themollo.app.mollo.tutorial.tutorial_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.LoginActivity;
import themollo.app.mollo.survey.DoSurveyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tutorial_p5 extends Fragment {


    public Tutorial_p5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial_p5, container, false);
        return view;
    }

}
