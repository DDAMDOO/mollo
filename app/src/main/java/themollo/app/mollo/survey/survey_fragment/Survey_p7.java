package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.share.Share;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.survey.SurveyResultPopup;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p7 extends FragUtilBasement implements FragmentLifeCycle {

    @BindView(R.id.sbHours)
    SeekBar sbHours;

    @BindView(R.id.tvHours)
    TextView tvHours;

    private String KEY = ORDINARY_DAY_DISORDER;
    private int VALUE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_p7, container, false);
        butterbind(view);

        sbHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                putSurveyDataPref(getActivity().getBaseContext(), KEY, progress+"");
                tvHours.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        prefLog("p7 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p7 paused");

    }

}
