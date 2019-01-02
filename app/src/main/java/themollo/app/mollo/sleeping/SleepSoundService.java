package themollo.app.mollo.sleeping;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import themollo.app.mollo.R;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.util.AppUtilBasement;

/**
 * Created by alex on 2018. 8. 21..
 */

public class SleepSoundService extends Service {

    public MediaPlayer mediaPlayer;

    public SleepSoundService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean msg = intent.getExtras().getBoolean(AppUtilBasement.MSG_KEY);
        int sleepSound = Integer.parseInt(getAlarmData(AppUtilBasement.MY_SLEEP_SOUND));

        if (msg) {
            startSleepSound(sleepSound);
        } else {
            stopSleepSound();
        }

        return START_NOT_STICKY;
    }

    public void startSleepSound(int sleepSound) {
        mediaPlayer = MediaPlayer.create(this, sleepSound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Log.i("sleep_sound", "sleep sounds service activated");

        Intent mainIntent = new Intent(this, SleepActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.btn_star) //honey sleep icon
                        .setContentTitle(getString(R.string.app_name_2))
                        .setContentIntent(pendingIntent)
                        .setContentText(getString(R.string.now_playing));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());
    }

    public void stopSleepSound() {
        mediaPlayer.pause();
        Log.i("sleep_sound", "media play stopped");
    }

    public SharedPreferences getAlarmPref() {
        return getSharedPreferences(AppUtilBasement.ALARM, Context.MODE_PRIVATE);
    }

    public String getAlarmData(String key) {
        return getAlarmPref().getString(key, "");
    }

}

