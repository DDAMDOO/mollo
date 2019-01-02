package themollo.app.mollo.analysis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by alex on 2018. 8. 2..
 */

public class AnalysisPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public AnalysisPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayAnalysisFragment();
            case 1:
                return new MonthlyAnalysisFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
