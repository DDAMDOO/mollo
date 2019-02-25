package themollo.app.mollo.sample;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class BeforeActivity extends AppCompatActivity {
    View vBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Transition transition = new Slide();
//        transition.setDuration(1000);
//        getWindow().setSharedElementEnterTransition(transition);
//        getWindow().setSharedElementExitTransition(transition);
        setContentView(R.layout.activity_before);
        ButterKnife.bind(this);

//        vBefore.setTransitionName(transitionName);
        vBefore = findViewById(R.id.vBefore);
        vBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeforeActivity.this, AfterActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            BeforeActivity.this,
                            vBefore, "asd");
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }


}
