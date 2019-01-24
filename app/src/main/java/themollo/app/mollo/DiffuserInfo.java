package themollo.app.mollo;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DiffuserInfo extends AppCompatActivity {
    TextView textView; //결과를 띄어줄 TextView
    TextView reload; //reload버튼
    Elements contents_temp;
    Elements contents_other;
    Document doc = null;
    String Temperature;//결과를 저장할 문자열변수
    TextView intent;



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






        reload.setOnClickListener(new View.OnClickListener() {//onclicklistener를 연결하여 터치시 실행됨
            @Override
            public void onClick(View v) {
                new AsyncTask() {//AsyncTask객체 생성
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            doc = Jsoup.connect("http://www.weather.go.kr/weather/forecast/timeseries.jsp").get(); //기상청 페이지 로딩
                            contents_temp = doc.select("div.now_weather1");//셀렉터로 현재 날시를 가져옴
                           // doc = Jsoup.connect("https://weather.naver.com/rgn/townWetr.nhn").get();
                           // contents_other = doc.select("div.fl");//셀렉터로 현재 날시를 가져옴
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Temperature = "온도\t\t 풍향, 풍량\t\t 습도\t\t 강수량\n"+contents_temp.text() + "\n";//+contents_other.text()+"\n";

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        Log.i("TEMP", "" + Temperature);
                        textView.setText(Temperature);
                    }


                }.execute();

            }
        });




        //diffuser 시간 정하는 popup으로 이동
        intent = findViewById(R.id.tvDiffuserOn);
        intent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DiffuserInfo.this,DiffuserTime.class);
                startActivity(intent);
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
            final TextView textView=findViewById(R.id.daily_step);
            textView.setText(String.valueOf(aLong)+" steps" );
            //Total steps covered for that day
        }
    }
}
