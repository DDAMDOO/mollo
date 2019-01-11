package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p5 extends FragUtilBasement implements FragmentLifeCycle {
    
    @BindView(R.id.llsymp1)
    LinearLayout llsymp1;

    @BindView(R.id.llsymp2)
    LinearLayout llsymp2;

    @BindView(R.id.llsymp3)
    LinearLayout llsymp3;

    @BindView(R.id.llsymp4)
    LinearLayout llsymp4;

    @BindView(R.id.llsymp5)
    LinearLayout llsymp5;

    @BindView(R.id.llsymp6)
    LinearLayout llsymp6;

    @BindView(R.id.llsymp7)
    LinearLayout llsymp7;

    @BindView(R.id.llsymp8)
    LinearLayout llsymp8;

    @BindView(R.id.llsymp9)
    LinearLayout llsymp9;

    @BindView(R.id.ivsymp1)
    ImageView ivsymp1;

    @BindView(R.id.ivsymp2)
    ImageView ivsymp2;

    @BindView(R.id.ivsymp3)
    ImageView ivsymp3;

    @BindView(R.id.ivsymp4)
    ImageView ivsymp4;

    @BindView(R.id.ivsymp5)
    ImageView ivsymp5;

    @BindView(R.id.ivsymp6)
    ImageView ivsymp6;

    @BindView(R.id.ivsymp7)
    ImageView ivsymp7;

    @BindView(R.id.ivsymp8)
    ImageView ivsymp8;

    @BindView(R.id.ivsymp9)
    ImageView ivsymp9;

    private boolean isClicked_symp1 = false;
    private boolean isClicked_symp2 = false;
    private boolean isClicked_symp3 = false;
    private boolean isClicked_symp4 = false;
    private boolean isClicked_symp5 = false;
    private boolean isClicked_symp6 = false;
    private boolean isClicked_symp7 = false;
    private boolean isClicked_symp8 = false;
    private boolean isClicked_symp9 = false;

    private String KEY = WHAT_DISTURB;
    private static int VALUE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_p5, container, false);
        butterbind(view);
        
        
        setButtonValue();

        return view;
    }
    
    public void setButtonValue(){

        llsymp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp1){
                    ivsymp1.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp1 = false;
                    --VALUE;
                }else {
                    ivsymp1.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp1 = true;
                    ++VALUE;
                }
            }
        });

        llsymp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp2){
                    ivsymp2.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp2 = false;
                    --VALUE;
                }else {
                    ivsymp2.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp2 = true;
                    ++VALUE;
                }
            }
        });

        llsymp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp3){
                    ivsymp3.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp3 = false;
                    --VALUE;
                }else {
                    ivsymp3.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp3 = true;
                    ++VALUE;
                }
            }
        });

        llsymp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp4){
                    ivsymp4.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp4 = false;
                    --VALUE;
                }else {
                    ivsymp4.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp4 = true;
                    ++VALUE;
                }
            }
        });

        llsymp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp5){
                    ivsymp5.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp5 = false;
                    --VALUE;
                }else {
                    ivsymp5.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp5 = true;
                    ++VALUE;
                }
            }
        });

        llsymp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp6){
                    ivsymp6.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp6 = false;
                    --VALUE;
                }else {
                    ivsymp6.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp6 = true;
                    ++VALUE;
                }
            }
        });

        llsymp7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp7){
                    ivsymp7.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp7 = false;
                    --VALUE;
                }else {
                    ivsymp7.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp7 = true;
                    ++VALUE;
                }
            }
        });

        llsymp8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp8){
                    ivsymp8.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp8 = false;
                    --VALUE;
                }else {
                    ivsymp8.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp8 = true;
                    ++VALUE;
                }
            }
        });

        llsymp9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked_symp9){
                    ivsymp9.setImageResource(R.drawable.radio_not_clicked);
                    isClicked_symp9 = false;
                    --VALUE;
                }else {
                    ivsymp9.setImageResource(R.drawable.radio_clicked);
                    isClicked_symp9 = true;
                    ++VALUE;
                }
            }
        });
        
    }

    public void setRadioValue(RadioButton r){
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r.isChecked()){
                    r.setChecked(false);
                }
            }
        });
    }

    public void setButtonValue(FrameLayout frameLayout, final ImageView imageView, final String KEY){
        SharedPreferences pref = getContext().getSharedPreferences(SURVEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY, false);
        editor.commit();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = pref.getBoolean(KEY, false);
                if(!isChecked) {
                    imageView.setVisibility(View.VISIBLE);
                    editor.putBoolean(KEY, true);
                    ++VALUE;
                    prefLog(VALUE + "");
                }else{
                    imageView.setVisibility(View.GONE);
                    editor.putBoolean(KEY, false);
                    --VALUE;
                    prefLog(VALUE + "");
                }
                editor.commit();
            }
        });
    }

    @Override
    public void butterbind(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void onResumeFragment(Context context) {
        prefLog("p5 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p5 paused");
        putSurveyDataPref(context, KEY, VALUE+"");
    }

    public static int getVALUE() {
        return VALUE;
    }

    public static void setVALUE(int VALUE) {
        Survey_p5.VALUE = VALUE;
    }
}
