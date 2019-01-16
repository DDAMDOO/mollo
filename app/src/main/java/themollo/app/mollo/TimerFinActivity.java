package themollo.app.mollo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import themollo.app.mollo.home.HomeActivity;
import themollo.app.mollo.util.BackPressController;

public class TimerFinActivity extends AppCompatActivity {

    TextView intent;
    private BackPressController backPressController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_fin);

        backPressController = new BackPressController(this);

        intent = findViewById(R.id.tvHome);
        intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerFinActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }
}
