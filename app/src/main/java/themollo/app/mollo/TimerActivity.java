package themollo.app.mollo;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends Activity {

    int value=0;
    TextView mText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mText=(TextView)findViewById(R.id.txt);

        Intent intent = getIntent();
        String cdVal = intent.getStringExtra("countdown_val");

        int cd_val = Integer.parseInt(cdVal);
        Log.d("abcd",cdVal+"");
        // 핸들러를 사용하지 않고도 일정시간마다 (혹은 후에) 코스를 수행할수 있도록
        // CountDownTimer 클래스가 제공된다.
        // '총시간'  과 '인터벌(간격)' 을 주면 매 간격마다 onTick 메소드를 수행한다.
        new CountDownTimer(cd_val*1000*60, 1000){
            int result = cd_val*60;
            @Override
            public void onTick(long millisUntilFinished) { // 총 시간과 주기
                Log.d("valueofcd",result+"");
                result--;
                int hour = result/3600;
                int min = (result-hour)/60;
                int sec = result%60;
                mText.setText(hour+":"+min+":"+sec);
            }

            @Override
            public void onFinish() {

            }
        }.start();  // 타이머 시작
    }


//    현재 시간 나타내주는 코드. 새로고침 안됨
//    long now = System.currentTimeMillis();
//    // 현재시간을 date 변수에 저장한다.
//    Date date = new Date(now);
//    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//    // nowDate 변수에 값을 저장한다.
//    String formatDate = sdfNow.format(date);
//
//    TextView dateNow;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timer);
//
//        dateNow = (TextView) findViewById(R.id.txt);
//        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당
//    }

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