package themollo.app.mollo.survey;

import android.content.Context;

/**
 * Created by alex on 2018. 7. 25..
 */

public interface FragmentLifeCycle {
    void onResumeFragment(Context context);
    void onPauseFragment(Context context);
}
