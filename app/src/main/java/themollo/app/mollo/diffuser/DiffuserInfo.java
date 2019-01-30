package themollo.app.mollo.diffuser;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import themollo.app.mollo.R;

public class DiffuserInfo extends AppCompatActivity {
    TextView textView; //결과를 띄어줄 TextView
    TextView reload; //reload버튼
    TextView btn;
    Elements contents_temp;
    Document doc = null;
    String Temperature;//결과를 저장할 문자열변수
    float temp;
    float wind;
    float hum;
    float rain;
    TextView intent;

    //향 추천 텍스트 선언부
    TextView rc_scent;

    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffuser_info);

        textView = (TextView) findViewById(R.id.current_temp);
        reload = (TextView) findViewById(R.id.temperature);
        rc_scent = findViewById(R.id.rc_scent_frame);
        btn = findViewById(R.id.tvDiffuserPower);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.CONFIG_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                .useDefaultAccount()
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {


                                //Async To fetch steps
                                new FetchStepsAsync().execute();

                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                }
                            }
                        }
                ).addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            DiffuserInfo.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        authInProgress = true;
                                        result.startResolutionForResult(DiffuserInfo.this, REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                    }
                                }
                            }
                        }
                ).build();
        mClient.connect();


        new AsyncTask() {//AsyncTask객체 생성
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    doc = Jsoup.connect("http://www.weather.go.kr/weather/forecast/timeseries.jsp").get(); //기상청 페이지 로딩
                    //contents_temp = doc.select("div.now_weather1");//셀렉터로 현재 날시를 가져옴
                    //contents_temp = doc.select(".now_weather1");
                    //contents_temp = doc.select(".now_weather1_center.temp1.MB10");
                    contents_temp = doc.select(".now_weather1_center");

                    // doc = Jsoup.connect("https://weather.naver.com/rgn/townWetr.nhn").get();
                    // contents_other = doc.select("div.fl");//셀렉터로 현재 날시를 가져옴
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Temperature="";
                //Temperature = "온도\t\t풍량\t\t습도\t\t강수량\n";// + contents_temp.text()+"\n";
                int cnt = 0;//숫자를 세기위한 변수
                for(Element element: contents_temp) {
                    cnt++;

                    if(cnt==1) {
                        temp = Float.parseFloat(element.text().substring(0, element.text().length() - 1));
                        Temperature += temp + "℃  ";
                    }
                    if(cnt==2){
                        wind=Float.parseFloat(element.text().substring(2,element.text().length()-3));
                        Temperature += wind +"m/s   ";
                    }

                    if(cnt==3){
                        hum=Float.parseFloat(element.text().substring(0,element.text().length()-1));
                        Temperature += hum +"%   ";
                    }

                    if(cnt == 4) {
                        Log.d("rain",element.text()+element.text().length());
                        if(element.text().length()>1) {
                            rain=Float.parseFloat(element.text().substring(0,element.text().length()-1));
                        }
                        else{
                            rain=0;
                            Temperature += rain +"mm/h";
                        }

                        break;
                    }
                }
                Log.d("asdf",""+temp +"\t"+ wind+"\t"+hum+"\t"+rain);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.i("TEMPINFO", "" + Temperature);
                textView.setText(Temperature);
            }

        }.execute();


        //diffuser 시간 정하는 popup으로 이동
        intent = findViewById(R.id.tvDiffuserOn);
        intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiffuserInfo.this, DiffuserTime.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] options = new String[]{"1단계", "2단계", "3단계"};
                final int[] selectedIndex={0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(DiffuserInfo.this);
                dialog.setTitle("디퓨저 세기를 선택하세요")
                        .setSingleChoiceItems(options,
                                0,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedIndex[0]=which;
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DiffuserInfo.this, options[selectedIndex[0]],Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dg = dialog.create();
                dg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 200, 200, 200)));
                dg.show();
                }

        });
    }


    private class FetchStepsAsync extends AsyncTask<Object, Object, Long> {
        protected Long doInBackground(Object... params) {
            long total = 0;
            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
            DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                if (totalSet != null) {
                    total = totalSet.isEmpty()
                            ? 0
                            : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                }
            } else {
                Log.w("api", "There was a problem getting the step count.");
            }
            return total;
        }

        @Override
        protected void onPostExecute(Long aLong) {

            super.onPostExecute(aLong);
            final TextView textView = findViewById(R.id.daily_step);
            textView.setText(String.valueOf(aLong) + " steps");
            //Total steps covered for that day

            if(aLong>=1000&&temp>5){

                rc_scent.setText(String.valueOf("페퍼민트"));
            }
            else
                rc_scent.setText(String.valueOf("라벤더"));
        }
    }
}