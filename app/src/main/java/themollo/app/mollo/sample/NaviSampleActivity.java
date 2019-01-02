package themollo.app.mollo.sample;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class NaviSampleActivity extends AppCompatActivity {

    @BindView(R.id.dlDrawerLayout)
    DrawerLayout dlDrawerLayout;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.llDrawer)
    LinearLayout llDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_sample);

        ButterKnife.bind(this);

        dlDrawerLayout.setScrimColor(Color.TRANSPARENT);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlDrawerLayout, R.string.open, R.string.close){

            private float scaleXFactor = 1.5f;
            private float scaleYFactor = 8f;
            private float scaleFactor = 3f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                llContent.setTranslationX(slideX);
                llContent.setScaleX(1-(slideOffset / scaleXFactor));
                llContent.setScaleY(1-(slideOffset / scaleYFactor));
            }
        };

        dlDrawerLayout.addDrawerListener(actionBarDrawerToggle);

    }
}
