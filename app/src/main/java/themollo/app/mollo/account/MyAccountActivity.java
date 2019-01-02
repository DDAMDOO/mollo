package themollo.app.mollo.account;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.share.Share;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.LoginActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class MyAccountActivity extends AppUtilBasement {

    @BindView(R.id.llBack)
    LinearLayout llBack;

    @BindView(R.id.civAccount)
    CircleImageView civAccount;

    @BindView(R.id.civAccountState)
    CircleImageView civAccountState;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvLoginType)
    TextView tvLoginType;

    @BindView(R.id.llLogout)
    LinearLayout llLogout;

    @BindView(R.id.flAccount)
    FrameLayout flAccount;

    private String loginType = "";

    @Override
    protected void onResume() {

        loginType = getLoginData(LOGIN_TYPE);
        setMyAccountData(loginType);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        butterBind();
        setButtonListener();


    }

    public void onPhoto(View view){
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.appColor));

        RxPaparazzo.single(this)
                .crop(options)
                .usingFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    if(response.resultCode() != RESULT_OK)
                        return;
                    bind(response.data());
                });
    }

    public void bind(FileData fileData){
        File file = fileData.getFile();
        if(file != null && file.exists()){
            Glide.with(civAccount.getContext())
                    .load(file)
                    .into(civAccount);
        }else{
            @SuppressLint("RestrictedApi") Drawable drawable = AppCompatDrawableManager.get()
                    .getDrawable(civAccount.getContext(), R.drawable.com_facebook_button_icon);
            civAccount.setImageDrawable(drawable);
        }
    }

    @Override
    public void setButtonListener() {
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }else if(getFirebaseUser() != null){
                    getFirebaseAuth().signOut();
                    moveTo(LoginActivity.class);
                }else{
                    moveTo(LoginActivity.class);
                }
            }
        });

        flAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhoto(v);
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void setMyAccountData(String loginType) {

        if(loginType.equals(FIREBASE_ANONYMOUS_LOGIN))
            tvLoginType.setText("Login " + loginType);
        else
            tvLoginType.setText("Login with " + loginType);

        tvName.setText(getLoginData(MY_NAME));
        showPD();
        if(loginType.equals(FIREBASE_ANONYMOUS_LOGIN)){
            Glide.with(civAccount.getContext())
                    .load(R.drawable.imgroot)
                    .into(civAccount);
            Glide.with(civAccountState.getContext())
                    .load(R.drawable.anony)
                    .into(civAccountState);
        }else if(loginType.equals(FIREBASE_EMAIL_LOGIN)){
            Glide.with(civAccount.getContext())
                    .load(R.drawable.imgroot)
                    .into(civAccount);
            Glide.with(civAccountState.getContext())
                    .load(R.drawable.email)
                    .into(civAccountState);
        }else if(loginType.equals(KAKAO_LOGIN)){
            String profileURL = getLoginData(PROFILE_PATH_URL);

            Glide.with(civAccount.getContext())
                    .load(profileURL)
                    .into(civAccount);

            Glide.with(civAccountState.getContext())
                    .load(R.drawable.kakao_icon)
                    .into(civAccountState);

        }else if(loginType.equals(FACEBOOK_LOGIN)){
            String profileURL = getLoginData(PROFILE_PATH_URL);

            Glide.with(civAccount.getContext())
                    .load(profileURL)
                    .into(civAccount);

            Glide.with(civAccountState.getContext())
                    .load(R.drawable.facebook_circled)
                    .into(civAccountState);
        }
        stopPD();

    }



    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
