package themollo.app.mollo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DiffuserInfo extends AppCompatActivity {
    TextView textView; //결과를 띄어줄 TextView
    TextView reload; //reload버튼
    Elements contents;
    Document doc = null;
    String Temperature;//결과를 저장할 문자열변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffuser_info);

        textView = (TextView) findViewById(R.id.current_temp);
        reload = (TextView) findViewById(R.id.temperature);

        reload.setOnClickListener(new View.OnClickListener() {//onclicklistener를 연결하여 터치시 실행됨
            @Override
            public void onClick(View v) {
                new AsyncTask() {//AsyncTask객체 생성
                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            doc = Jsoup.connect("http://www.weather.go.kr/weather/forecast/timeseries.jsp").get(); //기상청 페이지 로딩
                            contents = doc.select("dd.now_weather1_center.temp1.MB10");//셀렉터로 현재 날시를 가져옴
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Temperature = "현재날씨 = " + contents.text() + "\n";

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
    }
}
