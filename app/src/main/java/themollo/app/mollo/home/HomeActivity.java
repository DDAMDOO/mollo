package themollo.app.mollo.home;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import themollo.app.mollo.bluetooth.MainActivity;
import themollo.app.mollo.diffuser.DiffuserInfo;
import themollo.app.mollo.account.MyAccountActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.alarm.AlarmActivity;
import themollo.app.mollo.analysis.AnalysisActivity;
import themollo.app.mollo.lullaby.SleepSoundActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.BackPressController;
import themollo.app.mollo.util.DiffuserPopup;

public class HomeActivity extends AppUtilBasement {

    @BindString(R.string.button_transition)
    String transitionName;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

//    @BindColor(R.color.moving_circle_color)
//    int movingColor;

    @BindView(R.id.dlHomeLayout)
    DrawerLayout dlHomeLayout;

    @BindView(R.id.llHomeMenu)
    LinearLayout llHomeMenu;

//    @BindView(R.id.llSettings)
//    LinearLayout llSettings;

    @BindView(R.id.rlContent)
    RelativeLayout rlContent;

    @BindView(R.id.ivDrawerArrow)
    ImageView ivDrawerArrow;

    @BindView(R.id.llStore)
    LinearLayout llStore;

    @BindView(R.id.llAlarm)
    LinearLayout llAlarm;

    @BindView(R.id.llLullaby)
    LinearLayout llLullaby;

    @BindView(R.id.llSleepPattern)
    LinearLayout llSleepPattern;

//    @BindView(R.id.llDevice)
//    LinearLayout llDevice;

    @BindView(R.id.llMyAccount)
    LinearLayout llAccount;

    @BindView(R.id.llDiffuserMenu)
    LinearLayout llDiffuserMenu;

    @BindView(R.id.tvLullabyButton)
    TextView tvLullabyButton;

    @BindView(R.id.tvDiffuserButton)
    TextView tvDiffuserButton;

//    @BindView(R.id.ttvCurTime)
//    TimelyShortTimeView ttvCurTime;

//    @BindView(R.id.rlAlarmButton)
//    RelativeLayout rlAlarmButton;

//    @BindView(R.id.tvStartAlarmTime)
//    TextView tvStartAlarmTime;
//
//    @BindView(R.id.tvEndAlarmTime)
//    TextView tvEndAlarmTime;

    @BindView(R.id.ivTopCircle)
    ImageView ivTopCircle;

    @BindView(R.id.llDrawer)
    LinearLayout llDrawer;

    @BindView(R.id.tvAlarmTime)
    TextView tvAlarmTime;

    private DrawerArrow drawerArrow;
    private float drawerOffset;
    private boolean flipped;
    private BackPressController backPressController;
    private Timer timer;

    public void moveTo(Class cls){
        startActivity(new Intent(getBaseContext(), cls));
    }

    @Override
    protected void onResume() {


//        String sleepTime = getAlarmData(SLEEP_TIME);
        String wakeupTime = getAlarmData(WAKEUP_TIME);
        tvAlarmTime.setText(wakeupTime);
//        tvStartAlarmTime.setText(sleepTime+"");
//        tvEndAlarmTime.setText(wakeupTime+"");

//        timer = new Timer();
//        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1500);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        timer.cancel();
//        timer = null;
    }

    private void transitionOverride(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(4000);
            getWindow().setSharedElementEnterTransition(changeBounds);
            getWindow().setSharedElementExitTransition(changeBounds);

//            Fade fade = new Fade();
//            fade.setDuration(1500);
//            getWindow().setEnterTransition(fade);
//            getWindow().setExitTransition(fade);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterBind();
        setButtonListener();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionOverride();
            ivTopCircle.setTransitionName(transitionName);
        }

        backPressController = new BackPressController(this);

        dlHomeLayout.setScrimColor(Color.TRANSPARENT);
        dlHomeLayout.setDrawerElevation(0f);

        drawerArrow = new DrawerArrow(getResources());
        drawerArrow.setStrokeColor(getResources().getColor(R.color.light_gray));
        ivDrawerArrow.setImageDrawable(drawerArrow);

//        ttvCurTime.setTextColor(Color.WHITE);
//        ttvCurTime.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN);
//        ttvCurTime.setSeperatorsTextSize(50);
//        ttvCurTime.setTime("99:99");
//        ttvCurTime.setTime("00:00");

        registerActionToggle();

    }

    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }

    public void registerActionToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this, dlHomeLayout, R.string.open, R.string.close) {
            private float scaleYFactor = 8f;
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = (float) (drawerView.getWidth() * (1.2) * slideOffset * (1.2));
                rlContent.setTranslationX(slideX);
                rlContent.setScaleY(1 - (slideOffset / scaleYFactor));
            }
        };

        dlHomeLayout.addDrawerListener(actionBarDrawerToggle);

    }

    @OnClick({R.id.tvDiffuserButton})
    void moveToDevice() {
        Intent intent = new Intent(HomeActivity.this, DiffuserInfo.class);
        startActivity(intent);
    }



    @OnClick(R.id.ivTopCircle)
    void moveToAlarmAnim() {
//        moveTo(AlarmActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(ivTopCircle, transitionName));
//            Intent intent = new Intent(this, SketchBook.class); //for debug
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else{
//            moveTo(SketchBook.class); // for debug
            moveTo(AlarmActivity.class);
        }
    }

    @OnClick(R.id.tvLullabyButton)
    void moveToLullabyAnim() {
//        moveTo(LullabyActivity.class);

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(tvLullabyButton, transitionName));
            Intent intent = new Intent(this, SleepSoundActivity.class);
            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else{
//            moveTo(LullabyActivity.class);
            moveTo(SleepSoundActivity.class);
        }
    }


    @Override
    public void setButtonListener() {
        llHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlHomeLayout.openDrawer(Gravity.LEFT);
            }
        });

        dlHomeLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                drawerOffset = slideOffset;

                if (slideOffset >= .500) {
                    flipped = true;
                    drawerArrow.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrow.setFlip(flipped);
                }

                drawerArrow.setParameter(drawerOffset);
            }
        });

        llStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mollo.kr/"));
                startActivity(intent);
            }
        });

        llAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(AlarmActivity.class);
            }
        });

        llLullaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SleepSoundActivity.class);
            }
        });

        llSleepPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(AnalysisActivity.class);
            }
        });

        llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(MyAccountActivity.class);
            }
        });

//    //Diffuser Menu를 위한 버튼 이벤트
        llDiffuserMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { moveTo(DiffuserInfo.class); }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }





}
