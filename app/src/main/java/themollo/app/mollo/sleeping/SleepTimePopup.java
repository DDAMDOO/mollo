package themollo.app.mollo.sleeping;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.MySeekArc;

/**
 * Created by alex on 2018. 8. 20..
 */

public class SleepTimePopup extends AppUtilBasement {

    @BindView(R.id.tvSoundSleepTimeHour)
    TextView tvSoundSleepTimeHour;

    @BindView(R.id.tvSoundSleepTimeMin)
    TextView tvSoundSleepTimeMin;

    @BindView(R.id.tvHours)
    TextView tvHours;

    @BindView(R.id.tvMins)
    TextView tvMins;

    @BindView(R.id.saSoundTime)
    MySeekArc saSoundTime;

    @BindView(R.id.llConfirm)
    LinearLayout llConfirm;

    private String arcProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sleep_time_popup);
        butterBind();
        setButtonListener();

    }

    public boolean isOverOneHour(int progress){
        if(progress >= 60)
            return true;
        else return false;
    }

    @Override
    protected void onResume() {
        saSoundTime.setProgress(toInt(getAlarmData(SLEEP_SOUND_TIMER)));
        super.onResume();
    }

    @Override
    protected void onPause() {
        putAlarmTimeData(SLEEP_SOUND_TIMER, arcProgress);
        super.onPause();
    }

    @Override
    public void setButtonListener() {
        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putAlarmTimeData(SLEEP_SOUND_TIMER, arcProgress);
                finish();
            }
        });

        saSoundTime.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {

                arcProgress = progress+"";

                if(isOverOneHour(progress)){
                    tvSoundSleepTimeHour.setVisibility(View.VISIBLE);
                    tvHours.setVisibility(View.VISIBLE);
                    tvSoundSleepTimeHour.setText(""+(progress/60));
                    tvSoundSleepTimeMin.setText(""+(progress%60));
                }else{
                    tvSoundSleepTimeHour.setVisibility(View.GONE);
                    tvHours.setVisibility(View.GONE);
                    tvSoundSleepTimeMin.setText(""+progress);
                }
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {

            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {

            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

}
