package themollo.app.mollo.sleeping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
import themollo.app.mollo.R;
import themollo.app.mollo.analysis.AnalysisActivity;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.lullaby.SleepSoundActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class SleepingActivity extends AppUtilBasement {

    @BindInt(R.integer.from_sleep_activity_request_code)
    int requestCode;

    @BindView(R.id.llSleepSound)
    LinearLayout llSleepSound;

    @BindView(R.id.llSoundBottom)
    LinearLayout llSoundBottom;

    @BindView(R.id.llPlayPause)
    LinearLayout llPlayPause;

    @BindView(R.id.llPlayTime)
    LinearLayout llPlayTime;

//    @BindView(R.id.tvSleepSound)
//    TextView tvSleepSound;
//
//    @BindView(R.id.tvPlayPause)
//    TextView tvPlayPause;
//
//    @BindView(R.id.tvPlayTime)
//    TextView tvPlayTime;

    @BindView(R.id.rlSleepSound)
    RelativeLayout rlSleepSound;

    @BindView(R.id.rlPlayPause)
    RelativeLayout rlPlayPause;

    @BindView(R.id.rlPlayTime)
    RelativeLayout rlPlayTime;


    @BindView(R.id.ivFabImage)
    ImageView ivFabImage;
    
    @BindView(R.id.ppbSound)
    PlayPauseButton ppbSound;

    @BindView(R.id.tvWakeup)
    TextView tvWakeup;

    @BindView(R.id.tvWakeupTime)
    TextView tvWakeupTime;

    @BindView(R.id.tvAlarmTime)
    TextView tvAlarmTime;

    private boolean isFabOpen = false;
    private Animation fabCloseAnim, fabOpenAnim;
    private Intent serviceIntent;
    private int mySleepSound = 0;
    private Timer timer;

    @Override
    protected void onResume() {
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1500);
        serviceIntent = new Intent(this, SleepSoundService.class);
        tvWakeupTime.setText("ALARM " + getAlarmData(WAKEUP_TIME));
//        tvCurSound.setText(getAlarmData(TITLE));

        mySleepSound = toInt(getAlarmData(MY_SLEEP_SOUND));

        setMediaPlayService(true);

        super.onResume();
    }

    @Override
    protected void onPause() {
        timer.cancel();
        timer = null;
        setMediaPlayService(false);
        serviceIntent = null;
        super.onPause();
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date now = new Date();
                        String mins = now.getMinutes()+"";
                        if(now.getMinutes() < 10)
                            mins = "0" + now.getMinutes();
                        else
                            mins = now.getMinutes()+"";
                        tvAlarmTime.setText(now.getHours() + ":" + mins);
//                        ttvCurTime.setTime(now);
                    }
                });
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep);
        butterBind();
        setButtonListener();

        ppbSound.startAnimation();
        ppbSound.setColor(Color.parseColor("#7376ff"));
        fabOpenAnim = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_close);
    }

    @Override
    public void setButtonListener() {

        tvWakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(AnalysisActivity.class);
            }
        });

        llPlayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SleepingActivity.this, SleepTimePopup.class));
                fabAnim();
            }
        });

        llSleepSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnim();
                Intent intent = new Intent(SleepingActivity.this, SleepSoundActivity.class);
                startActivityForResult(new Intent(intent), requestCode);
            }
        });

        ppbSound.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
            @Override
            public void onStatusChange(View view, boolean state) {
                setMediaPlayService(state);
            }
        });

        llSoundBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnim();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public void setMediaPlayService(boolean isResume) {
        serviceIntent.putExtra(MSG_KEY, isResume);
        serviceIntent.putExtra(MY_SLEEP_SOUND, mySleepSound);
        startService(serviceIntent);
    }


    public void fabAnim() {
        if (isFabOpen) {
            ivFabImage.setImageResource(R.drawable.lullaby);


            llPlayPause.startAnimation(fabCloseAnim);
            llSleepSound.startAnimation(fabCloseAnim);
            llPlayTime.startAnimation(fabCloseAnim);
//            tvPlayPause.startAnimation(fabCloseAnim);
//            tvSleepSound.startAnimation(fabCloseAnim);
//            tvPlayTime.startAnimation(fabCloseAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rlPlayPause.setVisibility(View.INVISIBLE);
                    rlSleepSound.setVisibility(View.INVISIBLE);
                    rlPlayTime.setVisibility(View.INVISIBLE);
                }
            }, 200);


            llPlayPause.setClickable(false);
            llSleepSound.setClickable(false);
            llPlayTime.setClickable(false);
            isFabOpen = false;
        } else {
            ivFabImage.setImageResource(R.drawable.cancel);

            rlPlayPause.setVisibility(View.VISIBLE);
            rlSleepSound.setVisibility(View.VISIBLE);
            rlPlayTime.setVisibility(View.VISIBLE);

//            tvPlayPause.startAnimation(fabOpenAnim);
//            tvSleepSound.startAnimation(fabOpenAnim);
//            tvPlayTime.startAnimation(fabOpenAnim);
            llPlayPause.startAnimation(fabOpenAnim);
            llSleepSound.startAnimation(fabOpenAnim);
            llPlayTime.startAnimation(fabOpenAnim);
            llPlayPause.setClickable(true);
            llSleepSound.setClickable(true);
            llPlayTime.setClickable(true);
            isFabOpen = true;
        }
    }
}
