package themollo.app.mollo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            //Do Something
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(TimerActivity.this, TimerActivity.class); // xxx가 현재 activity,
                //yyy가 이동할 activity
                startActivity(i);
                finish();
            }
        }, 1000); // 1000ms
    }


//    long IAvailableDate;
//    long ILeaveDay;
//    Integer mDay,mHour,mMin,mSec;
//    TextView timer_text;
//    String timer_sec;
//
//    private TimerTask second;
//    private final Handler handler = new Handler();
//
//    int dYear = 2012;
//    int dMonth = 0; //1월은 0부터
//    int dDay = 4;
//    int dHour = 18;
//    int dMin = 26;
//    int dSec = 00;
//
//    public void setTimer(int Year, int Month, int Day, int Hour, int Min, int Sec){
//        this.dYear = Year;
//        this.dMonth = Month;
//        this.dDay = Day;
//        this.dHour = Hour;
//        this.dMin = Min;
//        this.dSec = Sec;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timer);
//        timer_text = (TextView) findViewById(R.id.txt);
//
//        //아래 날짜를 바꿔주면 됨 (년,월,일,시,분,초)
//        setTimer(2013,0,7,18,00,00);
//        testStart();
//    }
//
//    public void testStart() {
//
//
//        Calendar gameDate = null;
//        gameDate = Calendar.getInstance();
//        gameDate.set(dYear,dMonth,dDay,dHour,dMin,dSec);
//        IAvailableDate = gameDate.getTimeInMillis();
//
//        second = new TimerTask() {
//
//            @Override
//            public void run() {
//
//                Calendar gameDate2 = null;
//                gameDate2 = Calendar.getInstance();
//                ILeaveDay = IAvailableDate - gameDate2.getTimeInMillis();
//
//                if(ILeaveDay > 0){
//                    gameDate2.setTimeInMillis(ILeaveDay);
//                    mDay = gameDate2.get(Calendar.DATE);
//                    if(mDay > 1){
//                        //며칠인지 표현 안하고 시간으로만 표시하기 위해 처리
//                        mHour = ((mDay-1)*24)+(gameDate2.get(Calendar.HOUR_OF_DAY)-9);
//                    }else{
//                        mHour = gameDate2.get(Calendar.HOUR_OF_DAY)-9;
//                    }
//                    mMin = gameDate2.get(Calendar.MINUTE);
//                    mSec = gameDate2.get(Calendar.SECOND);
//                }
//
//
//                Log.i("Test", "Timer start");
//
//                if(mHour == null && mMin == null && mSec == null || mHour == 0 && mMin == 0 && mSec == 0){
//                    timer_sec = "종료";
//                }else{
//                    timer_sec = "남은시간 "+mHour+" : "+mMin+" : "+mSec;
//                }
//
//                Update();
//
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(second, 0, 1000);
//    }
//
//    protected void Update() {
//        Runnable updater = new Runnable() {
//            public void run() {
//                timer_text.setText(timer_sec);
//            }
//        };
//        handler.post(updater);
//    }
}