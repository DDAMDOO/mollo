package themollo.app.mollo.splash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import themollo.app.mollo.R;
import themollo.app.mollo.tutorial.TutorialActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class SplashActivity extends AppUtilBasement {

    private static final int SPLASH_DELAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTo(TutorialActivity.class);
            }
        }, SPLASH_DELAY_TIME);

    }


    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {

    }

    public void getKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KEYHASH",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
