package themollo.app.mollo.survey;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.kakao.auth.Session;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.home.HomeActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.R;
import themollo.app.mollo.util.BackPressController;

public class DoSurveyActivity extends AppUtilBasement {

//    @BindView(R.id.ciIndicator)
//    CircleIndicator ciIndicator;

    @BindView(R.id.vpSurvey)
    ViewPager vpSurvey;

    @BindView(R.id.tvCurPageNum)
    TextView tvCurPageNum;

    @BindView(R.id.llNext)
    LinearLayout llNext;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.tvStart)
    TextView tvStart;

    private SurveyPagerAdapter surveyPagerAdapter;
    private BackPressController backPressController;
    private int curPageNum = 0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_survey);
        butterBind();
        setButtonListener();

        Log.i("loginType", "loginType : " + getLoginData(LOGIN_TYPE));

        backPressController = new BackPressController(this);

        surveyPagerAdapter = new SurveyPagerAdapter(getSupportFragmentManager());

        vpSurvey.setAdapter(surveyPagerAdapter);
        vpSurvey.setCurrentItem(0);


//        ciIndicator.setViewPager(vpSurvey);
////        ciIndicator.setBackgroundColor(R.color.appColor);
//
//        surveyPagerAdapter.registerDataSetObserver(ciIndicator.getDataSetObserver());

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                vpSurvey.setCurrentItem(tag);
            }
        };

        vpSurvey.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int curPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    llBack.setVisibility(View.GONE);
                    llNext.setVisibility(View.VISIBLE);
                    tvStart.setVisibility(View.GONE);
                }else if(position == 6){
                    llBack.setVisibility(View.VISIBLE);
                    llNext.setVisibility(View.GONE);
                    tvStart.setVisibility(View.VISIBLE);

                }else{
                    llBack.setVisibility(View.VISIBLE);
                    llNext.setVisibility(View.VISIBLE);
                    tvStart.setVisibility(View.GONE);
                }
                tvCurPageNum.setText(""+(position+1));
            }

            @Override
            public void onPageSelected(int newPosition) {
                FragmentLifeCycle fragToShow = (FragmentLifeCycle) surveyPagerAdapter.getItem(newPosition);
                fragToShow.onResumeFragment(getBaseContext());

                FragmentLifeCycle fragToHide = (FragmentLifeCycle) surveyPagerAdapter.getItem(curPosition);
                fragToHide.onPauseFragment(getBaseContext());

                curPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }

    public void putSurveyDataPref(String key, String value) {
        SharedPreferences.Editor editor = getSurveyPref().edit();
        editor.putString(key, value);
        editor.commit();

        Log.i("pref", "key : " + key + " value : " + value);
    }

    public ViewPager getViewPager() {
        return vpSurvey;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        surveyPagerAdapter.unregisterDataSetObserver(ciIndicator.getDataSetObserver());
    }

    @Override
    public void setButtonListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpSurvey.setCurrentItem(--curPageNum);
            }
        });

        llNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpSurvey.setCurrentItem(++curPageNum);
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoSurveyActivity.this, SurveyResultPopup.class));
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }


}
