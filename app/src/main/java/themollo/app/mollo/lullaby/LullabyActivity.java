package themollo.app.mollo.lullaby;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
import themollo.app.mollo.R;
import themollo.app.mollo.sleeping.SleepActivity;
import themollo.app.mollo.sleeping.SleepSoundService;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.LullabyAnimator;

public class LullabyActivity extends AppUtilBasement {

    @BindInt(R.integer.from_sleep_activity_request_code)
    int fromSleepActivityRequestCode;

    @BindString(R.string.button_transition)
    String transitionName;

    @BindView(R.id.pbProgrssBar)
    ProgressBar pbProgressBar;

    @BindView(R.id.rvLullabyList)
    RecyclerView rvLullabyList;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.llOK)
    LinearLayout llOK;

    @BindView(R.id.ppbPlayPauseButton)
    PlayPauseButton ppbPlayPauseButton;


    private static final String NOT_START = "notstart";
    private static final String PLAYING = "playing";
    private static final String PAUSE = "pause";

    private static final String JK_URL = "https://www.youtube.com/watch?v=DXw38O1Y7PE";
    private MediaPlayer mediaPlayer;
    private Drawable boot = new LullabyAnimator();
    private LullabyAdapter lullabyAdapter;
    private ArrayList<LullabyModel> lullabyData = new ArrayList<>();
    private boolean fromSleep = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == fromSleepActivityRequestCode)
                fromSleep = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {



        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lullaby);
        butterBind();



        if (fromSleep) {
            llBack.setVisibility(View.GONE);
            llOK.setVisibility(View.VISIBLE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pbProgressBar.setTransitionName(transitionName);
        }
        pbProgressBar.setIndeterminateDrawable(boot);

        lullabyData.add(new LullabyModel(ttRain, "03:30", false, R.raw.rainy_day));
        lullabyData.add(new LullabyModel(ttTrain, "03:00", false, R.raw.train));
        lullabyData.add(new LullabyModel(ttOcean, "05:30", false, R.raw.ocean_wave));
        lullabyData.add(new LullabyModel(ttBrook, "04:30", false, R.raw.waterbrook));
        lullabyData.add(new LullabyModel(ttForest, "01:50", false, R.raw.forest));


        rvLullabyList.setHasFixedSize(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        rvLullabyList.setLayoutManager(linearLayoutManager);

        lullabyAdapter = new LullabyAdapter(lullabyData);
        rvLullabyList.setAdapter(lullabyAdapter);

        ppbPlayPauseButton.setColor(Color.parseColor("#8B8AFF"));
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
    public void setButtonListener() {

    }

    @Override
    public void onBackPressed() {
        if (!fromSleep) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        } else {
            Intent intent = new Intent(LullabyActivity.this, SleepActivity.class);
            if (isAtLeastOneItemSelected()) {

                startActivity(intent);
            }
        }
        super.onBackPressed();
    }

    @OnClick(R.id.llBack)
    void backPress() {
        if (!fromSleep) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        } else {
            Intent intent = new Intent(LullabyActivity.this, SleepActivity.class);
            if (isAtLeastOneItemSelected()) {

                startActivity(intent);
            }
        }
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public boolean isAtLeastOneItemSelected() {
        for (int i = 0; i < lullabyData.size(); i++) {
            if (!lullabyData.get(i).isSelected()) {
                Toast.makeText(this, getString(R.string.select_sound), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    public class LullabyAdapter extends RecyclerView.Adapter {

        ArrayList<LullabyModel> data = new ArrayList<>();

        public LullabyAdapter(ArrayList<LullabyModel> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.lullaby_view, parent, false);
            return new LullabyViewHolder(item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LullabyModel lullabyModel = data.get(position);
            LullabyViewHolder lullabyViewHolder = (LullabyViewHolder) holder;
            String title = lullabyModel.getLullabyTitle();
            int file = getRawFileDataFromTitle(title);

            lullabyViewHolder.tvLullabyTitle.setText("" + title);
            lullabyViewHolder.tvPlayTime.setText("" + lullabyModel.getLullabyPlayTime());
            if (lullabyModel.isSelected()) {
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_filled);
            } else {
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_empty);
            }

            lullabyViewHolder.ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelected(false);
                    }
                    data.get(position).setSelected(true);
                    lullabyAdapter.notifyDataSetChanged();

                    putAlarmTimeData(MY_SLEEP_SOUND, file+"");
                    putAlarmTimeData(TITLE, title);
                    Log.i("title", "title : " + title);
                }
            });

            lullabyViewHolder.ppbPlayState.setColor(Color.WHITE);
            lullabyViewHolder.ppbPlayState.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
                @Override
                public void onStatusChange(View view, boolean state) {
                    Log.i("mpmp", "state : " + state);
                    if (state) {
                        mediaPlayer = MediaPlayer.create(getBaseContext(), lullabyModel.getRawFileName());
                        mediaPlayer.start();
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public class LullabyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvLullabyTitle)
        TextView tvLullabyTitle;
        @BindView(R.id.tvPlayTime)
        TextView tvPlayTime;
        @BindView(R.id.rlHeart)
        RelativeLayout rlHeart;
        @BindView(R.id.ivHeart)
        ImageView ivHeart;
        @BindView(R.id.ppbPlayState)
        PlayPauseButton ppbPlayState;
        @BindView(R.id.llLullabyView)
        LinearLayout llLullabyView;


        public LullabyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

