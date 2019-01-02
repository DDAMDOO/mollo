package themollo.app.mollo.tutorial;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class TutorialActivity extends AppUtilBasement {

    @BindView(R.id.ciIndicator)
    CircleIndicator ciIndicator;
    @BindView(R.id.vpTutorial)
    ViewPager vpTutorial;

    private TutorialPagerAdapter tutorialPagerAdapter;

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

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

        tutorialPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());

        vpTutorial.setAdapter(tutorialPagerAdapter);
        vpTutorial.setCurrentItem(0);

        ciIndicator.setViewPager(vpTutorial);
//        ciIndicator.setBackgroundColor(R.color.appColor);

        tutorialPagerAdapter.registerDataSetObserver(ciIndicator.getDataSetObserver());

        View.OnClickListener movePageListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                vpTutorial.setCurrentItem(tag);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tutorialPagerAdapter.unregisterDataSetObserver(ciIndicator.getDataSetObserver());
    }

    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
