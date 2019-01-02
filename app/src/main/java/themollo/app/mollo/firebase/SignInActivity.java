package themollo.app.mollo.firebase;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;

public class SignInActivity extends FirebaseLogin {

    @BindView(R.id.etUserID)
    TextInputEditText etUserID;
    @BindView(R.id.etUserPwd)
    EditText etUserPwd;
    @BindView(R.id.llSignUp)
    LinearLayout llSignUp;
    @BindView(R.id.llSignIn)
    LinearLayout llSignIn;
    @BindView(R.id.llBack)
    LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        butterBind();

        setButtonListener();
    }

    @Override
    public void setButtonListener() {
        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SignUpActivity.class);
            }
        });

        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseEmailSignIn(etUserID.getText().toString()
                        , etUserPwd.getText().toString()
                        , getBaseContext());
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
