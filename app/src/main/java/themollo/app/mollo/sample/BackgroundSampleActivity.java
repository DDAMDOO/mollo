package themollo.app.mollo.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class BackgroundSampleActivity extends AppUtilBasement {



    @BindView(R.id.llChart)
    LinearLayout llChart;

    private int activityType = 0;

    @Override
    protected void onStart() {
        super.onStart();
        activityType = Integer.parseInt(getIntent().getStringExtra(exampleName));
        Log.i("activitytype",activityType+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_sample);
        switch (activityType){
            case R.id.btHome:
                homeOnCreate();
                break;
        }

    }

    public void chartOnCreate(){
        setContentView(R.layout.chart);
        ButterKnife.bind(this);
    }

    public void homeOnCreate(){
        setContentView(R.layout.activity_background_sample);
        ButterKnife.bind(this);
    }

    public void lullabyOnCreate(){

    }

    public void alarmOnCreate(){

    }



    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {

    }
}
