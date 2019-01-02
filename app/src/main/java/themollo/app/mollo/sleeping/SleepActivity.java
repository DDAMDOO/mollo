package themollo.app.mollo.sleeping;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;
import com.mbh.timelyview.TimelyShortTimeView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
import themollo.app.mollo.R;
import themollo.app.mollo.analysis.AnalysisActivity;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class SleepActivity extends AppUtilBasement {

    @BindInt(R.integer.from_sleep_activity_request_code)
    int requestCode;

    @BindString(R.string.alarm_to_sleep)
    String alarmToSleep;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    @BindView(R.id.pbSleepProgressBar)
    ProgressBar pbSleepProgressBar;

    @BindView(R.id.ttvCurTime)
    TimelyShortTimeView ttvCurTime;

    @BindView(R.id.gifBack)
    GifImageView gifBack;

    @BindView(R.id.tvStopButton)
    TextView tvStopButton;

    @BindView(R.id.ppbSound)
    PlayPauseButton ppbSound;

    @BindView(R.id.tvStartAlarmTime)
    TextView tvStartAlarmTime;

    @BindView(R.id.tvEndAlarmTime)
    TextView tvEndAlarmTime;

    @BindView(R.id.llSleepSound)
    LinearLayout llSleepSound;

    @BindView(R.id.llSoundBottom)
    LinearLayout llSoundBottom;

    @BindView(R.id.llPlayPause)
    LinearLayout llPlayPause;
    
    @BindView(R.id.llPlayTime)
    LinearLayout llPlayTime;

    @BindView(R.id.tvSleepSound)
    TextView tvSleepSound;

    @BindView(R.id.tvPlayPause)
    TextView tvPlayPause;

    @BindView(R.id.tvPlayTime)
    TextView tvPlayTime;

    @BindView(R.id.rlSleepSound)
    RelativeLayout rlSleepSound;

    @BindView(R.id.rlPlayPause)
    RelativeLayout rlPlayPause;

    @BindView(R.id.rlPlayTime)
    RelativeLayout rlPlayTime;
    
    @BindView(R.id.tvCurSound)
    TextView tvCurSound;

    @BindView(R.id.ivFabImage)
    ImageView ivFabImage;

    private Calendar calendar;
    private Drawable boot = new SleepingAnimator();
    private Timer timer;
    private String sleepTime;
    private String wakeupTime;
    private boolean sleepTimeDayOver;
    private boolean isFabOpen = false;
    private Animation fabOpenAnim, fabCloseAnim;
    private PopupWindow popupWindow;
    private Intent serviceIntent;
    private int mySleepSound;
    private static MediaPlayer mediaPlayer;
    private String gifURL = "";
    private int timerArcProgress = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        butterBind();
        setButtonListener();

//        ppbSound.setPlayed(false);
        ppbSound.startAnimation();
        ppbSound.setColor(Color.WHITE);

        sleepTime = getIntent().getStringExtra(SLEEP_TIME);
        wakeupTime = getIntent().getStringExtra(WAKEUP_TIME);
        sleepTimeDayOver = getIntent().getBooleanExtra(SLEEP_TIME_DAY_OVER, false);
        calendar = Calendar.getInstance();
        fabOpenAnim = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        int wakeupHour = toInt(wakeupTime.split(":")[0]);
        int wakeupMin = toInt(wakeupTime.split(":")[1]);


        calendar.set(Calendar.HOUR_OF_DAY, wakeupHour);
        calendar.set(Calendar.MINUTE, wakeupMin);

        tvStartAlarmTime.setText(sleepTime);
        tvEndAlarmTime.setText(wakeupTime);

        Intent alarmIntent = new Intent("themollo.app.mollo.sleeping.AlarmStart");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                R.integer.pendingIntentRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionOverride();
            pbSleepProgressBar.setTransitionName(alarmToSleep);
        }

        pbSleepProgressBar.setIndeterminateDrawable(boot);
        ttvCurTime.setTextColor(Color.WHITE);
        ttvCurTime.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN);
        ttvCurTime.setSeperatorsTextSize(50);
        ttvCurTime.setTime("99:99");
        ttvCurTime.setTime("00:00");

    }

    private void transitionOverride() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(4000);
            getWindow().setSharedElementExitTransition(changeBounds);
            getWindow().setSharedElementEnterTransition(changeBounds);

//            Fade fade = new Fade();
//            fade.setDuration(4000);
//            getWindow().setEnterTransition(fade);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press stop button to stop alarm", Toast.LENGTH_SHORT).show();
        return;
    }

    public int getMinutesForHandler(){
        return 1000 * 60 * timerArcProgress;
    }

    @Override
    protected void onResume() {
        serviceIntent = new Intent(this, SleepSoundService.class);

        tvCurSound.setText(getAlarmData(TITLE));
        timerArcProgress = toInt(getAlarmData(SLEEP_SOUND_TIMER));
        if(timerArcProgress != 0) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMediaPlayService(false);
                }
            }, getMinutesForHandler());
        }

        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        gifBack.startAnimation(fadeOut);
        gifURL = getGifURLFromTitle(getAlarmData(TITLE));
        new GifDataDownloader() {
            @Override
            protected void onPostExecute(byte[] bytes) {
                Animation fadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
                gifBack.setBytes(bytes);
                gifBack.setAlpha(0.5f);
                gifBack.startAnimation(fadeIn);
            }
        }.execute(gifURL);
        mySleepSound = toInt(getAlarmData(MY_SLEEP_SOUND));
        setMediaPlayService(true);
        gifBack.startAnimation();
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1500);
        super.onResume();
    }

    @Override
    protected void onPause() {

        setMediaPlayService(false);
        gifBack.stopAnimation();
        timer.cancel();
        timer = null;
        serviceIntent = null;
        if(handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    public void setMediaPlayService(boolean isResume) {
        serviceIntent.putExtra(MSG_KEY, isResume);
        serviceIntent.putExtra(MY_SLEEP_SOUND, mySleepSound);
        startService(serviceIntent);
    }

    @Override
    public void setButtonListener() {
        tvStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SleepActivity.this, AnalysisActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        llPlayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SleepActivity.this, SleepTimePopup.class));
                fabAnim();
            }
        });

        llSleepSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnim();
                Intent intent = new Intent(SleepActivity.this, LullabyActivity.class);
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

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date now = new Date();
                        ttvCurTime.setTime(now);
                    }
                });
            }
        };
    }

    public void fabAnim() {
        if (isFabOpen) {
            ivFabImage.setImageResource(R.drawable.lullaby);
            
            
            llPlayPause.startAnimation(fabCloseAnim);
            llSleepSound.startAnimation(fabCloseAnim);
            llPlayTime.startAnimation(fabCloseAnim);
            tvPlayPause.startAnimation(fabCloseAnim);
            tvSleepSound.startAnimation(fabCloseAnim);
            tvPlayTime.startAnimation(fabCloseAnim);
            
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

            tvPlayPause.startAnimation(fabOpenAnim);
            tvSleepSound.startAnimation(fabOpenAnim);
            tvPlayTime.startAnimation(fabOpenAnim);
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
