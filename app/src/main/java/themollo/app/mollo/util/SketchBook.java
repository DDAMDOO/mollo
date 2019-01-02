package themollo.app.mollo.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbh.timelyview.TimelyView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import themollo.app.mollo.R;
import themollo.app.mollo.sleeping.SleepActivity;


public class SketchBook extends AppUtilBasement{
    private volatile ObjectAnimator objectAnimator = null;

    @BindString(R.string.button_transition)
    String transitionName;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    @BindString(R.string.alarm_to_sleep)
    String alarmToSleep;

    @BindView(R.id.timelyTextView)
    TimelyView timelyTextView;

    @BindView(R.id.saSleep)
    MySeekArc saSleep;

    @BindView(R.id.saWakeup)
    MySeekArc saWakeup;

    @BindView(R.id.tvUpperSeekArc)
    TextView tvUpperSeekArc;

    @BindView(R.id.tvLowerSeekArc)
    TextView tvLowerSeekArc;

    @BindView(R.id.tvTotalSleepTime)
    TextView tvTotalSleepTime;

    @BindView(R.id.tvFollowSleepTime)
    TextView tvFollowSleepTime;

    @BindView(R.id.tvFollowWakeupTime)
    TextView tvFollowWakeupTime;

    @BindView(R.id.pbAlarmProgressBar)
    ProgressBar pbAlarmProgressBar;

    @BindView(R.id.ttvTens)
    TimelyView ttvTens;

    @BindView(R.id.ttvUnits)
    TimelyView ttvUnits;

    private int sleepArcValue = 0;
    private int wakeupArcValue = 0;
    private volatile int from = 9;
    private volatile int to = 1;
    private int topThumbXPos = 0;
    private int topThumbYPos = 0;
    private int bottomThumbXPos = 0;
    private int bottomThumbYPos = 0;

    private boolean isFirst = true;
    private boolean isSleepTimeOverToday = false;

    float px_300dp;
    float px_150dp;

    private Drawable boot = new LullabyAnimator();

    private void setDefaultArcData() {
        int startArcProgress = toInt(getAlarmData(SLEEP_ARC_PROGRESS));
        int endArcProgress = toInt(getAlarmData(WAKEUP_ARC_PROGRESS));
        String sleepTime = getAlarmData(SLEEP_TIME);
        String wakeupTime = getAlarmData(WAKEUP_TIME);
        topThumbXPos = toInt(getAlarmData(TOP_ARC_XPOS));
        topThumbYPos = toInt(getAlarmData(TOP_ARC_YPOS));
        bottomThumbXPos = toInt(getAlarmData(BOTTOM_ARC_XPOS));
        bottomThumbYPos = toInt(getAlarmData(BOTTOM_ARC_YPOS));

        saSleep.setProgress(startArcProgress);
        saWakeup.setProgress(endArcProgress);

        tvFollowSleepTime.setText(sleepTime);
        tvFollowWakeupTime.setText(wakeupTime);

        tvFollowSleepTime.setTranslationX(topThumbXPos);
        tvFollowSleepTime.setTranslationY(topThumbYPos);
        tvFollowWakeupTime.setTranslationX(bottomThumbXPos);
        tvFollowWakeupTime.setTranslationY(bottomThumbYPos);

        Log.i("alarmdata", "top x : " + getAlarmData(TOP_ARC_XPOS) + " top y : " + getAlarmData(TOP_ARC_YPOS));
        Log.i("alarmdata", "bottom x : " + getAlarmData(BOTTOM_ARC_XPOS) + " bottom y : " + getAlarmData(BOTTOM_ARC_YPOS));

    }

    @Override
    protected void onStart() {
//        putAlarmTimeData(SLEEP_TIME, "22:00");
//        putAlarmTimeData(WAKEUP_TIME, "07:00");
//        putAlarmTimeData(SLEEP_ARC_PROGRESS, 240+"");
//        putAlarmTimeData(WAKEUP_ARC_PROGRESS, 60+"");
//        putAlarmTimeData(TOP_ARC_XPOS, 95+"");
//        putAlarmTimeData(TOP_ARC_YPOS, 9+"");
//        putAlarmTimeData(BOTTOM_ARC_XPOS, 758+"");
//        putAlarmTimeData(BOTTOM_ARC_YPOS, 125+"");
        setDefaultArcData();
        super.onStart();
    }

    @Override
    protected void onResume() {
        setDefaultArcData();
        super.onResume();
    }

    private void transitionOverride() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(1500);
            getWindow().setSharedElementExitTransition(changeBounds);
            getWindow().setSharedElementEnterTransition(changeBounds);

//            Fade fade = new Fade();
//            fade.setMode(Fade.MODE_IN);
//            fade.setDuration(1500);
//            getWindow().setEnterTransition(fade);
//            fade.setDuration(4000);
//            getWindow().setExitTransition(fade);
        }
    }

    private void putAlarmData() {
        putAlarmTimeData(SLEEP_ARC_PROGRESS, getSleepArcValue() + "");
        putAlarmTimeData(WAKEUP_ARC_PROGRESS, getWakeupArcValue() + "");
        putAlarmTimeData(SLEEP_TIME, tvFollowSleepTime.getText().toString());
        putAlarmTimeData(WAKEUP_TIME, tvFollowWakeupTime.getText().toString());
        putAlarmTimeData(TOP_ARC_XPOS, topThumbXPos + "");
        putAlarmTimeData(TOP_ARC_YPOS, topThumbYPos + "");
        putAlarmTimeData(BOTTOM_ARC_XPOS, bottomThumbXPos + "");
        putAlarmTimeData(BOTTOM_ARC_YPOS, bottomThumbYPos + "");
    }

    @Override
    protected void onPause() {
        putAlarmData();
        super.onPause();
    }

    @Override
    protected void onStop() {
        putAlarmData();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_book);
        butterBind();
        setButtonListener();
        initUI();


//        Log.i("layoutparams", "arc pl : " + saSleep.getPaddingLeft() + " arc pr : " + saSleep.getPaddingRight());


    }

    public void initUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionOverride();
            tvFollowSleepTime.setTransitionName(alarmStartTime);
            tvFollowWakeupTime.setTransitionName(alarmEndTime);
            pbAlarmProgressBar.setTransitionName(transitionName);
        }

        pbAlarmProgressBar.setIndeterminateDrawable(boot);

        int white = ContextCompat.getColor(getBaseContext(), R.color.white);
        timelyTextView.setTextColor(white);
        timelyTextView.setStrokeWidth(4);
        ttvUnits.setTextColor(white);
        ttvUnits.setStrokeWidth(4);
        ttvTens.setTextColor(white);
        ttvTens.setStrokeWidth(4);

        sleepArcValue = 600;
        wakeupArcValue = 0;

//        saSleep.setStartAngle(SLLEP_ARC_START_ANGLE);
//        saWakeup.setStartAngle(WAKEUP_ARC_START_ANGLE);

        objectAnimator = timelyTextView.animate(0, 9);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        px_300dp = dpToPx(300, getBaseContext());
        px_150dp = dpToPx(150, getBaseContext());

        tvFollowSleepTime.setTranslationX(29);
        tvFollowSleepTime.setTranslationY(363);

        tvFollowWakeupTime.setTranslationX(758);
        tvFollowWakeupTime.setTranslationY(438);
    }

    public String getTimeText(int progress, String type) {
        StringBuilder sb = new StringBuilder();
        int hour = 0;
        int min = 0;

        if (type.equals("sleepTime")) {
            if (progress < 360) {
                hour = 18 + progress / 60;
                min = progress % 60;
            } else {
                hour = -6 + progress / 60;
                min = progress % 60;
            }
        } else if (type.equals("wakeupTime")) {
            hour = 6 + progress / 60;
            min = progress % 60;
        }

        if (isUnderTen(hour)) {
            sb.append("0" + hour + ":");
            if (isUnderTen(min)) {
                sb.append("0" + min);
            } else {
                sb.append(min);
            }
        } else {

            sb.append(hour + ":");
            if (isUnderTen(min)) {
                sb.append("0" + min);
            } else {
                sb.append(min);
            }
        }

        return sb.toString();
    }

    public boolean isUnderTen(int i) {
        if (i < 10 && i >= 0) return true;
        else return false;
    }


//    public int getThumbWindowXPos(MySeekArc seekArc) {
//        return seekArc.getWidth() - seekArc.getPaddingRight() - seekArc.getPaddingLeft();
//    }
//
//    public int getThumbWindowYPos(MySeekArc seekArc) {
//        return seekArc.getHeight() - seekArc.getPaddingTop() - seekArc.getPaddingBottom();
//    }

    public static float dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    @OnClick(R.id.llBack)
    void backPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @OnClick(R.id.tvSleepButton)
    void sleep() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(pbAlarmProgressBar, alarmToSleep));
            Intent intent = new Intent(this, SleepActivity.class);
            intent.putExtra(SLEEP_TIME, tvFollowSleepTime.getText().toString());
            intent.putExtra(WAKEUP_TIME, tvFollowWakeupTime.getText().toString());
            intent.putExtra(SLEEP_TIME_DAY_OVER, isSleepTimeOverToday);
            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            moveTo(SleepActivity.class);
        }
    }

    public int getSleepArcValue() {
        return sleepArcValue;
    }

    public int getWakeupArcValue() {
        return wakeupArcValue;
    }

    public int getTotalSleepHourValue() {
        int res = (720 - getSleepArcValue()) + getWakeupArcValue();
        return res;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void setButtonListener() {
        saSleep.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {
                sleepArcValue = progress;
//                to = getTotalSleepHourValue() / 60;
//
//                if (from != to) {
//                    timelyTextView.animate(from, to).setDuration(800).start();
//                    from = to;
//                }

                overTenControl();

                tvFollowSleepTime.setText(getTimeText(progress, "sleepTime"));

                if (!isFirst) {
                    if (progress < 360) {
                        topThumbXPos = mySeekArc.getThumbXPos() - 150;
                        tvFollowSleepTime.setTranslationX(topThumbXPos);
                        isSleepTimeOverToday = false;
                    } else {
                        topThumbXPos = mySeekArc.getThumbXPos() + 20;
                        tvFollowSleepTime.setTranslationX(topThumbXPos);
                        isSleepTimeOverToday = true;
                    }
                    topThumbYPos = mySeekArc.getThumbYPos() - 50;
                    tvFollowSleepTime.setTranslationY(topThumbYPos);
                }


                Log.i("thumb", "saSleep x : " + tvFollowSleepTime.getTranslationX()
                        + " y : " + tvFollowSleepTime.getTranslationY());
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {
                isFirst = false;
            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {

            }
        });


        saWakeup.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {

                wakeupArcValue = progress;
//                to = getTotalSleepHourValue() / 60;
//
//                if (from != to) {
//                    timelyTextView.animate(from, to).setDuration(800).start();
//                    from = to;
//                }

                overTenControl();

                tvFollowWakeupTime.setText(getTimeText(progress, "wakeupTime"));

                if (!isFirst) {
                    if (progress < 360) {
                        bottomThumbXPos = mySeekArc.getThumbXPos() + 20;
                        tvFollowWakeupTime.setTranslationX(bottomThumbXPos);
                    } else {
                        bottomThumbXPos = mySeekArc.getThumbXPos() - 150;
                        tvFollowWakeupTime.setTranslationX(bottomThumbXPos);
                    }
                    bottomThumbYPos = (int) (mySeekArc.getThumbYPos() - px_150dp);
                    tvFollowWakeupTime.setTranslationY(bottomThumbYPos);
                }


                Log.i("thumb", "saWakeup x : " + tvFollowWakeupTime.getTranslationX()
                        + " y : " + tvFollowWakeupTime.getTranslationY());
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {
                Log.i("thumb", "stopTracking");
                isFirst = false;
            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {
                Log.i("thumb", "stopTracking");
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public void overTenControl() {
        int tensFrom = from / 10;
        int unitsFrom = from % 10;

        to = getTotalSleepHourValue() / 60;

        int tensTo = to / 10;
        int unitsTo = to % 10;

//        Log.i("fromto", "from : " + from + " to : " + to);

        if (from != to) {
            if (from >= 10 && to >= 10) {
                Log.i("fromto", "tens over triggered");
                ttvTens.setVisibility(View.VISIBLE);
                ttvUnits.setVisibility(View.VISIBLE);
                timelyTextView.setVisibility(View.INVISIBLE);
                ttvTens.animate(tensFrom, tensTo).setDuration(500).start();
                ttvUnits.animate(unitsFrom, unitsTo).setDuration(500).start();
                from = to;
            }else if (from < 10 && to >= 10) {
                Log.i("fromto", "tens over triggered");
                ttvTens.setVisibility(View.VISIBLE);
                ttvUnits.setVisibility(View.VISIBLE);
                timelyTextView.setVisibility(View.INVISIBLE);
                ttvTens.animate(from, tensTo).setDuration(500).start();
                ttvUnits.animate(unitsFrom, unitsTo).setDuration(500).start();
                from = to;
            }else if(from >= 10 && to < 10){
                Log.i("fromto", "tens below triggered");
                ttvTens.setVisibility(View.INVISIBLE);
                ttvUnits.setVisibility(View.INVISIBLE);
                timelyTextView.setVisibility(View.VISIBLE);
                timelyTextView.animate(unitsFrom, to).setDuration(500).start();
                from = to;
            }else if(from < 10 && to < 10){
                Log.i("fromto", "tens below triggered");
                ttvTens.setVisibility(View.INVISIBLE);
                ttvUnits.setVisibility(View.INVISIBLE);
                timelyTextView.setVisibility(View.VISIBLE);
                timelyTextView.animate(from, to).setDuration(500).start();
                from = to;
            }
        }
    }


}





