package themollo.app.mollo.lullaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class NowPlayingActivity extends AppUtilBasement {

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.rlBackground)
    RelativeLayout rlBackground;

    @Override
    protected void onResume() {
        String type = getIntent().getExtras().getString(PLAY_TYPE);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        butterBind();
        setButtonListener();
    }

    @Override
    public void setButtonListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
