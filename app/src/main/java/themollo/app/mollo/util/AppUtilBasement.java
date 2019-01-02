package themollo.app.mollo.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.auth.Session;
import com.tsengvn.typekit.TypekitContextWrapper;

import themollo.app.mollo.R;
import themollo.app.mollo.alarm.AlarmActivity;
import themollo.app.mollo.home.HomeActivity;

/**
 * Created by alex on 2018. 7. 16..
 */

public abstract class AppUtilBasement extends AppCompatActivity{

    public static final String exampleName = "EN";
    public static final String CHART = "chart";
    public static final String ALARM = "alarm";
    public static final String LOGIN = "login";
    public static final String LOGIN_TYPE = "loginType";
    public static final String MY_NAME = "myName";
    public static final String PROFILE_PATH_URL = "profilePathUrl";
    public static final String IS_PROFILE_SELECTED_FROM_PHONE = "isProfileSelectedFromPhone";
    public static final String FIREBASE_ANONYMOUS_LOGIN = "anonymously";
    public static final String FIREBASE_EMAIL_LOGIN = "E-mail";
    public static final String KAKAO_LOGIN = "KakaoTalk";
    public static final String FACEBOOK_LOGIN = "Facebook";
    public static final String LULLABY = "lullaby";
    public static final String HOME = "home";
    public static final String TITLE = "title";
    public static final String SURVEY = "survey";
    public static final String SLEEP_ARC_PROGRESS = "sleepArcProgress";
    public static final String WAKEUP_ARC_PROGRESS = "wakeupArcProgress";
    public static final String SLEEP_TIME = "sleepTime";
    public static final String WAKEUP_TIME= "wakeupTime";
    public static final String TOP_ARC_XPOS = "topArcXPos";
    public static final String TOP_ARC_YPOS = "topArcYPos";
    public static final String BOTTOM_ARC_XPOS = "bottomArcXPos";
    public static final String BOTTOM_ARC_YPOS = "bottomArcYPos";
    public static final String SLEEP_TIME_DAY_OVER = "sleepTimeDayOver";
    public static final String SLEEP_SOUND_TIMER = "sleepSoundTimer";
    public static final String MSG_KEY = "msg_key";
    public static final String MY_SLEEP_SOUND = "mySleepSound";
    public static final String ttRain = "RAINY DAY";
    public static final String ttTrain = "TRAIN TRIP";
    public static final String ttOcean = "OCEAN WAVE";
    public static final String ttBrook = "WATER BROOK";
    public static final String ttForest = "SILENT FOREST";
    public static final String GifURLRain = "https://media.giphy.com/media/d1G6qsjTJcHYhzxu/giphy.gif";
    public static final String GifURLTrain = "https://media.giphy.com/media/orTe0RcDVe5mN3V8Mw/giphy.gif";
    public static final String GifURLOcean = "https://media.giphy.com/media/oX9s5QZitp5xxyQlZf/giphy.gif";
    public static final String GifURLBrook = "https://media.giphy.com/media/TgKGGM7CmyVnxx4mGw/giphy.gif";
    public static final String GifURLForest = "https://media.giphy.com/media/1AgEWxnMP9UUGPT4pL/giphy.gif";

    public static final String SEX = "sex";
    public static final String AGE = "age";

    public static final String BED_TIME = "bedtime";
    public static final String WHEN_FALL_ASLEEP = "whenfallasleep";

    public static final String DEEP_SLEEP_TIME = "deepsleeptime";
    public static final String WHAT_DISTURB = "whatdisturb";
    public static final String SLEEP_QUALITY = "sleepquality";
    public static final String DRUG_FOR_SLEEP = "drugforsleep";
    public static final String ORDINARY_DAY_DISORDER = "ordinarydaydisorder";


    public AlarmManager alarmManager;

    public int toInt(String s) {
        return Integer.parseInt(s);
    }

    public float toFloat(String s){
        return Float.parseFloat(s);
    }

    public void moveTo(Class cls){
        startActivity(new Intent(getBaseContext(), cls));
    }
    public void toastText(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public void setDefaultAlarmTimeData(){
        //default data
        putAlarmTimeData(SLEEP_TIME, "22:00");
        putAlarmTimeData(WAKEUP_TIME, "07:00");
        putAlarmTimeData(SLEEP_ARC_PROGRESS, 240+"");
        putAlarmTimeData(WAKEUP_ARC_PROGRESS, 60+"");
        putAlarmTimeData(TOP_ARC_XPOS, 95+"");
        putAlarmTimeData(TOP_ARC_YPOS, 9+"");
        putAlarmTimeData(BOTTOM_ARC_XPOS, 758+"");
        putAlarmTimeData(BOTTOM_ARC_YPOS, 125+"");
        putAlarmTimeData(SLEEP_SOUND_TIMER, 0+"");
        putAlarmTimeData(MY_SLEEP_SOUND, R.raw.rainy_day+"");
        putAlarmTimeData(TITLE, ttRain);
    }

    public void logUtil(String tag, String text){
        Log.i(tag, text);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
        MultiDex.install(newBase);
    }

    public void ifJoinedPassToHome(){
        if (Session.getCurrentSession().isOpened()
                || AccessToken.isCurrentAccessTokenActive()
                || getFirebaseUser() != null) {
            moveTo(HomeActivity.class);
        }
    }

    //*********************************************************************************************
    //              Progress Dialog
    //*********************************************************************************************


    public PendingIntent alarmEvent(){
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        return pendingIntent;
    }

    //*********************************************************************************************
    //              Progress Dialog
    //*********************************************************************************************

    public ProgressDialog pd;
    public void showPD(){
        if (pd == null) {
        pd = new ProgressDialog(this);
        pd.setCancelable(false); //임의 취소 불가
        pd.setMessage(".. loading ..");
        }
        pd.show();
    }

    public void stopPD(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    //*********************************************************************************************
    //              ALARM & SLEEP SOUND SHARED PREFERENCES
    //*********************************************************************************************

    public SharedPreferences getAlarmPref() {
        return getSharedPreferences(ALARM, Context.MODE_PRIVATE);
    }

    public void putAlarmTimeData(String key, String value){
        SharedPreferences.Editor editor = getAlarmPref().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getAlarmData(String key){
        return getAlarmPref().getString(key, "");
    }

    //*********************************************************************************************
    //              LOGIN SHARED PREFERENCES
    //*********************************************************************************************

    public SharedPreferences getLoginPref() {
        return getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    }

    public void putLoginData(String key, String value){
        SharedPreferences.Editor editor = getLoginPref().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getLoginData(String key){
        return getLoginPref().getString(key, "");
    }

    //*********************************************************************************************
    //              SURVEY SHARED PREFERENCES
    //*********************************************************************************************
    public SharedPreferences getSurveyPref() {
        return getSharedPreferences(SURVEY, Context.MODE_PRIVATE);
    }

    public void putSurveyDataPref(String key, String value) {
        SharedPreferences.Editor editor = getSurveyPref().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSurveyData(String key){
        return getSurveyPref().getString(key, "");
    }

    //*********************************************************************************************
    //              FIREBASE
    //*********************************************************************************************

    public FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    //*********************************************************************************************
    //              FILE DATA
    //*********************************************************************************************

    public int getRawFileDataFromTitle(String title) {
        switch (title) {
            case ttRain:
                return R.raw.rainy_day;
            case ttTrain:
                return R.raw.train;
            case ttBrook:
                return R.raw.waterbrook;
            case ttForest:
                return R.raw.forest;
            case ttOcean:
                return R.raw.ocean_wave;
            default:
                return 0;
        }
    }

    public String getGifURLFromTitle(String title){
        switch (title){
            case ttRain:
                return GifURLRain;
            case ttTrain:
                return GifURLTrain;
            case ttBrook:
                return GifURLBrook;
            case ttForest:
                return GifURLForest;
            case ttOcean:
                return GifURLOcean;
            default:
                return "hahaitsdefault";
        }
    }


    //abstract methods
    public abstract void setButtonListener();
    public abstract void butterBind();

}
