package themollo.app.mollo.util;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by alex on 2018. 7. 31..
 */

public class BackPressController {
    private long backKeyPressedTime = 0;
    private Activity activity;
    private Toast toast;

    public BackPressController(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (is2sPassed()){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(activity, "press back button one more to exit", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(is2sNotPassed()){
            shutDown();
            toast.cancel();
        }
    }
    public Boolean is2sPassed() {
            return System.currentTimeMillis() > backKeyPressedTime + 2000;
    }

    public Boolean is2sNotPassed() {
        return System.currentTimeMillis() <= backKeyPressedTime + 2000;
    }

    public void shutDown() {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAffinity();
        }
        else {
            activity.finishAffinity();
//            activity.finish();
        }
        System.exit(0);
    }
}
