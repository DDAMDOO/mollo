package themollo.app.mollo.alarm;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.sleeping.SleepingActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.MySeekArc;

public class AlarmActivity extends AppUtilBasement{

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    @BindString(R.string.alarm_to_sleep)
    String alarmToSleep;

    @BindView(R.id.saSleep)
    MySeekArc saSleep;

    @BindView(R.id.saWakeup)
    MySeekArc saWakeup;

    @BindView(R.id.tvFollowSleepTime)
    TextView tvFollowSleepTime;

    @BindView(R.id.tvFollowWakeupTime)
    TextView tvFollowWakeupTime;

    @BindView(R.id.tvSleepButton)
    TextView tvSleepButton;

    @BindView(R.id.tvTotalSleepTime)
    TextView tvTotalSleepTime;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    private int sleepArcValue = 0;
    private int wakeupArcValue = 0;
    private int topThumbXPos = 0;
    private int topThumbYPos = 0;
    private int bottomThumbXPos = 0;
    private int bottomThumbYPos = 0;

    private boolean isFirst = true;
    private boolean isSleepTimeOverToday = false;

    float px_300dp;
    float px_150dp;

    @Override
    protected void onResume() {
//        setDefaultAlarmTimeData(); //for debug
        setDefaultArcData();
        super.onResume();
    }

    @Override
    protected void onStart() {
//        setDefaultAlarmTimeData(); //for debug
        setDefaultArcData();
        super.onStart();
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        butterBind();
        setButtonListener();
        initUI();

    }

    @Override
    public void setButtonListener() {

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(SleepingActivity.class);
            }
        });

        saSleep.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {
                sleepArcValue = progress;
                int a = getTotalSleepHourValue() / 60;
                if(a>=10) {
                    tvTotalSleepTime.setText(a + "");
                }
                else {
                    tvTotalSleepTime.setText(a + "");
                }

                tvFollowSleepTime.setText(getTimeText(progress, "sleepTime"));

                if (!isFirst) {
                    if (progress < 360) {
                        topThumbXPos = mySeekArc.getThumbXPos() - 130;
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
                int a = getTotalSleepHourValue() / 60;
                if(a>=10) {
                    tvTotalSleepTime.setText(a + "");
                }
                else {
                    tvTotalSleepTime.setText(a + "");
                }

                tvFollowWakeupTime.setText(getTimeText(progress, "wakeupTime"));

                if (!isFirst) {
                    if (progress < 360) {
                        bottomThumbXPos = mySeekArc.getThumbXPos() + 30;
                        tvFollowWakeupTime.setTranslationX(bottomThumbXPos);
                    } else {
                        bottomThumbXPos = mySeekArc.getThumbXPos() - 130;
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

    private void initUI(){

        sleepArcValue = 600;
        wakeupArcValue = 0;

        px_300dp = dpToPx(300, getBaseContext());
        px_150dp = dpToPx(150, getBaseContext());

        tvFollowSleepTime.setTranslationX(29);
        tvFollowSleepTime.setTranslationY(363);

        tvFollowWakeupTime.setTranslationX(758);
        tvFollowWakeupTime.setTranslationY(438);
    }

    public static float dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
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

    }
}
