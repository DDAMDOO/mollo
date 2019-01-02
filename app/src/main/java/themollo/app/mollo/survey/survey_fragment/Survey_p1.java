package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p1 extends FragUtilBasement implements FragmentLifeCycle{

    @BindView(R.id.llMale)
    LinearLayout llMale;

    @BindView(R.id.llFemale)
    LinearLayout llFemale;

    private String KEY = SEX;
    private static String VALUE = "male";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_p1, container, false);
        butterbind(view);

        llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMale.setBackgroundColor(getResources().getColor(R.color.button_select_color));
                llFemale.setBackgroundColor(getResources().getColor(R.color.white));
                VALUE = getString(R.string.male);
            }
        });

        llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMale.setBackgroundColor(getResources().getColor(R.color.white));
                llFemale.setBackgroundColor(getResources().getColor(R.color.button_select_color));
                VALUE = getString(R.string.female);
            }
        });


        return view;
    }



    @Override
    public void butterbind(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void onResumeFragment(Context context) {
        prefLog("p1 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p1 paused");

        putSurveyDataPref(context, KEY, VALUE);

//        SharedPreferences.Editor editor
//                = context.getSharedPreferences(SURVEY, Context.MODE_PRIVATE).edit();
//        editor.putString(KEY, VALUE).commit();
//
//        prefLog("key : " + KEY + " value : " + VALUE);
    }

}
