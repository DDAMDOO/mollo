package themollo.app.mollo.tutorial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.LoginActivity;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.BackPressController;

public class TutorialActivity extends AppUtilBasement {

    @BindView(R.id.vpTutorial)
    ViewPager vpTutorial;

    @BindView(R.id.tvCurPageNum)
    TextView tvCurPageNum;

    @BindView(R.id.llNext)
    LinearLayout llNext;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.tvStart)
    TextView tvStart;

    private TutorialPagerAdapter tutorialPagerAdapter;
    private BackPressController backPressController;
    private int curPageNum = 0;

    @Override
    protected void onStart() {
        ifJoinedPassToHome();
        super.onStart();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        butterBind();
        setButtonListener();

        backPressController = new BackPressController(this);
        tutorialPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());

        vpTutorial.setAdapter(tutorialPagerAdapter);
        vpTutorial.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                vpTutorial.setCurrentItem(tag);
            }
        };

        vpTutorial.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    llBack.setVisibility(View.GONE);
                    llNext.setVisibility(View.VISIBLE);
                    tvStart.setVisibility(View.GONE);
                }else if(position == 4){
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
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setButtonListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpTutorial.setCurrentItem(--curPageNum);
            }
        });

        llNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpTutorial.setCurrentItem(++curPageNum);
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(TutorialActivity.this, DoSurveyActivity.class));
                startActivity(new Intent(TutorialActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
