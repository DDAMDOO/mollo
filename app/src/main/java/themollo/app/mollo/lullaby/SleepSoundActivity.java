package themollo.app.mollo.lullaby;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
import themollo.app.mollo.R;
import themollo.app.mollo.sleeping.SleepSoundPagerAdapter;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.AppUtilBasement;

public class SleepSoundActivity extends AppUtilBasement {

    @BindInt(R.integer.from_sleep_activity_request_code)
    int fromSleepActivityRequestCode;

    @BindView(R.id.ppbPlayPauseButton)
    PlayPauseButton ppbPlayPauseButton;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.vpSleepSound)
    ViewPager vpSleepSound;

//    @BindView(R.id.ivBottomHill)
//    ImageView ivBottomHill;

    private SleepSoundPagerAdapter sleepSoundPagerAdapter;
    private int rawData;
    private MediaPlayer mediaPlayer;
    private boolean fromSleep = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == fromSleepActivityRequestCode)
                fromSleep = true;
    }

    @Override
    protected void onResume() {
        rawData = R.raw.rainy_day;
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_sound);
        butterBind();
        setButtonListener();
        initUI();
    }

    public void initUI(){
        ppbPlayPauseButton.setColor(Color.WHITE);
        sleepSoundPagerAdapter = new SleepSoundPagerAdapter(getSupportFragmentManager());
        vpSleepSound.setAdapter(sleepSoundPagerAdapter);
        vpSleepSound.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                vpSleepSound.setCurrentItem(tag);
            }
        };

        vpSleepSound.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int curPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                if(mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }

                if(position == 0) { //rainy day
                    rawData = R.raw.rainy_day;
//                    ivBottomHill.setBackgroundResource(R.drawable.sleep_sound_bottom_hill_rainy);
                }else if(position == 1){ //water brook
                    rawData = R.raw.waterbrook;
//                    ivBottomHill.setBackgroundResource(R.drawable.sleep_sound_bottom_hill_brook);
                }else if(position == 2){ //train
                    rawData = R.raw.train;
//                    ivBottomHill.setBackgroundResource(R.drawable.sleep_sound_bottom_hill_train);
                }else if(position == 3){ //ocean wave
                    rawData = R.raw.ocean_wave;
//                    ivBottomHill.setBackgroundResource(R.drawable.sleep_sound_bottom_hill_oceanwave);
                }

                putAlarmTimeData(MY_SLEEP_SOUND, rawData+"");
            }

            @Override
            public void onPageSelected(int newPosition) {
                FragmentLifeCycle fragToShow = (FragmentLifeCycle) sleepSoundPagerAdapter.getItem(newPosition);
                fragToShow.onResumeFragment(getBaseContext());

                FragmentLifeCycle fragToHide = (FragmentLifeCycle) sleepSoundPagerAdapter.getItem(curPosition);
                fragToHide.onPauseFragment(getBaseContext());

                curPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    @Override
    public void setButtonListener() {

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ppbPlayPauseButton.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
            @Override
            public void onStatusChange(View view, boolean state) {
                Log.i("ppbstate", "state : " + state);
                if(state){
                    mediaPlayer = MediaPlayer.create(getBaseContext(), rawData);
                    mediaPlayer.start();
                }else{
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
