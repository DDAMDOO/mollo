package themollo.app.mollo.diffuser;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import themollo.app.mollo.R;
import themollo.app.mollo.util.BackPressController;

public class TimerActivity extends Activity {

    TextView mText;
    CountDownTimer timer;
    private BackPressController backPressController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mText = (TextView) findViewById(R.id.txt);

        backPressController = new BackPressController(this);

        Intent intent = getIntent();
        String cdVal = intent.getStringExtra("countdown_val");

        //타이머에서 설정한 시간을 가져온 intent
        int cd_val = Integer.parseInt(cdVal);
        if (cd_val == 0) {
            Intent intent1 = new Intent(TimerActivity.this, TimerFinActivity.class);
            startActivity(intent1);
            finish();
        }
        // 핸들러를 사용하지 않고도 일정시간마다 (혹은 후에) 코스를 수행할수 있도록
        // CountDownTimer 클래스가 제공된다.
        // '총시간'  과 '인터벌(간격)' 을 주면 매 간격마다 onTick 메소드를 수행한다.
        timer = new CountDownTimer(cd_val * 1000 * 61, 1000) {
            int result = cd_val * 60;

            @Override
            public void onTick(long millisUntilFinished) { // 총 시간과 주기
                int hour = result / 3600;
                int min = (result / 60) % 60;
                int sec = result % 60;
                Log.d("타이머", result + ""+hour+" "+min+" "+sec);
                String text = String.format("%02d : %02d : %02d", hour, min, sec);
                mText.setText(text);
                result--;
                if (hour == 0 && min == 0 && sec == 0) {
                    Toast.makeText(TimerActivity.this, "타이머가 종료되었습니다.\n디퓨저를 종료합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TimerActivity.this, TimerFinActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();  // 타이머 시작
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        Intent intent = new Intent(TimerActivity.this, DiffuserInfo.class);
        startActivity(intent);
    }
}