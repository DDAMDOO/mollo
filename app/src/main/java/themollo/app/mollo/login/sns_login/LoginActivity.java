package themollo.app.mollo.login.sns_login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.firebase.FirebaseLogin;
import themollo.app.mollo.firebase.SignInActivity;
import themollo.app.mollo.firebase.SignUpActivity;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.util.BackPressController;

public class LoginActivity extends FirebaseLogin {

    private static final String TAG = "LoginActivity";

    private ISessionCallback iSessionCallback;
    private CallbackManager callbackManager;
    private BackPressController backPressController;

//    @BindView(R.id.btAnonySignIn)
//    Button btAnonySignIn;

    @BindView(R.id.etUserEmail)
    EditText etUserEmail;

    @BindView(R.id.etUserPwd)
    EditText etUserPwd;

    @BindView(R.id.tvLoginSecret)
    TextView tvLoginSecret;

    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @BindView(R.id.tvSignUp)
    TextView tvSignUp;

//    @BindView(R.id.btSignIn)
//    Button btSignIn;

    @BindView(R.id.btFacebook)
    LoginButton btFacebook;

    @BindView(R.id.btKakaoLogin)
    com.kakao.usermgmt.LoginButton btKakaoLogin;

    @BindView(R.id.rlFacebook)
    RelativeLayout rlFacebook;

    @BindView(R.id.rlKakao)
    RelativeLayout rlKakao;

    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeSessionCallback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDefaultAlarmTimeData();
        ifJoinedPassToHome();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.sign_in);
        butterBind();

        backPressController = new BackPressController(this);
//for keyhash
        try{
            PackageInfo info = getPackageManager().getPackageInfo("themollo.app.mollo", PackageManager.GET_SIGNATURES);

            for(Signature signature :info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, "Keyhash: "+ Base64.encodeToString(md.digest(),Base64.DEFAULT));
                Log.d(TAG, "Keyhash1: "+Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        }catch(Exception e){}



        setRegisterKakaoCallback();
        setRegisterFacebookCallback();
        setButtonListener();
    }

    public void requestInfo(){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.i(TAG, "errmsg : " + errorResult.toString());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i(TAG, "user id : " + result.getId());
                Log.i(TAG, "user name : " + result.getNickname());
                Log.i(TAG, "email : " + result.getKakaoAccount().getEmail());
                Log.i(TAG, "profile path : " + result.getProfileImagePath());

                putLoginData(MY_NAME, result.getNickname());
                putLoginData(PROFILE_PATH_URL, result.getProfileImagePath());
            }
        });
    }

    @Override
    public void setButtonListener(){

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseEmailSignIn(etUserEmail.getText().toString()
                        , etUserPwd.getText().toString()
                        , getBaseContext());
            }
        });

        tvLoginSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAnonySignIn(getBaseContext());
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(SignUpActivity.class);
            }
        });

//        btSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                moveTo(SignInActivity.class);
//            }
//        });

        rlFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btFacebook.performClick();
            }
        });

        rlKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btKakaoLogin.performClick();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public void setRegisterKakaoCallback(){
        //kakao
        iSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                requestInfo();
                putLoginData(LOGIN_TYPE, KAKAO_LOGIN);
                moveTo(DoSurveyActivity.class);
                Log.i("kakao", "session opened");
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Log.i("kakao", "session open failed : " + exception.toString());
            }
        };
        Session.getCurrentSession().addCallback(iSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


    }
    
    public void setRegisterFacebookCallback(){
        //facebook
        callbackManager = CallbackManager.Factory.create();

        btFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        putLoginData(LOGIN_TYPE, FACEBOOK_LOGIN);
                        Log.i("facebook_info", object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("facebook", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                Profile profile = Profile.getCurrentProfile();
                //Log.i("facebook_info", profile.getName());
                Log.i("facebook_info", profile.getProfilePictureUri(100,100) + "");

                putLoginData(MY_NAME, profile.getName());
                putLoginData(PROFILE_PATH_URL, profile.getProfilePictureUri(100, 100)+"");

                moveTo(DoSurveyActivity.class);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("facebook_info", error.toString());
            }
        });


    }

    public void removeSessionCallback() {
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }

}
