package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

/**
 * A simple {@link Fragment} subclass.
 */
public class Survey_p2 extends FragUtilBasement implements FragmentLifeCycle{

    @BindView(R.id.tv1020s)
    TextView tv1020s;
    @BindView(R.id.tv3040s)
    TextView tv3040s;
    @BindView(R.id.tv50s)
    TextView tv50s;

    @BindView(R.id.ll1020s)
    LinearLayout ll1020s;
    @BindView(R.id.ll3040s)
    LinearLayout ll3040s;
    @BindView(R.id.ll50s)
    LinearLayout ll50s;

    private String KEY = AGE;
    private static String VALUE = "10-20s";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_p2, container, false);
        butterbind(view);

        tv1020s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll1020s.setBackgroundColor(getResources().getColor(R.color.button_select_color));
                ll3040s.setBackgroundColor(getResources().getColor(R.color.white));
                ll50s.setBackgroundColor(getResources().getColor(R.color.white));
                VALUE = getString(R.string.age1020s);
            }
        });

        tv3040s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll1020s.setBackgroundColor(getResources().getColor(R.color.white));
                ll3040s.setBackgroundColor(getResources().getColor(R.color.button_select_color));
                ll50s.setBackgroundColor(getResources().getColor(R.color.white));
                VALUE = getString(R.string.age3040s);
            }
        });

        tv50s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll1020s.setBackgroundColor(getResources().getColor(R.color.white));
                ll3040s.setBackgroundColor(getResources().getColor(R.color.white));
                ll50s.setBackgroundColor(getResources().getColor(R.color.button_select_color));
                VALUE = getString(R.string.age50s);
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
        prefLog("p2 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p2 paused");
        putSurveyDataPref(context, KEY, VALUE);
    }

}
