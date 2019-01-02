package themollo.app.mollo.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.alarm.AlarmActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.LoginActivity;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class MainActivity extends AppUtilBasement {

    @BindView(R.id.btLogout)
    Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.getCurrentSession().isOpened())
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            moveTo(LoginActivity.class);
                        }
                    });
                else if(AccessToken.isCurrentAccessTokenActive()){
                    LoginManager.getInstance().logOut();
                    moveTo(LoginActivity.class);
                }
            }
        });
        setButtonListener();
    }

    public void setListener(final int r){
        findViewById(r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BackgroundSampleActivity.class);
                intent.putExtra(exampleName, String.valueOf(r));
                startActivity(intent);
            }
        });
    }

    @Override
    public void setButtonListener() {
        findViewById(R.id.btSensor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SensorActivity.class);
            }
        });

        findViewById(R.id.btNavi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(NaviSampleActivity.class);
            }
        });

        findViewById(R.id.btLullabyPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(LullabyActivity.class);
            }
        });

        findViewById(R.id.btCircularBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(CircularBarActivity.class);
            }
        });

        findViewById(R.id.btAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(AlarmActivity.class);
            }
        });

        setListener(R.id.btHome);
    }

    @Override
    public void butterBind() {

    }
}
