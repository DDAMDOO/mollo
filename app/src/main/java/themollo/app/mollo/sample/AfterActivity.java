package themollo.app.mollo.sample;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class AfterActivity extends AppCompatActivity {

    View vAfter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Transition transition = new Slide();
//        transition.setDuration(6000);
//        getWindow().setSharedElementEnterTransition(transition);
//        getWindow().setSharedElementExitTransition(transition);
        setContentView(R.layout.activity_after);
        vAfter = findViewById(R.id.vAfter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vAfter.setTransitionName("asd");
        }

    }
}
