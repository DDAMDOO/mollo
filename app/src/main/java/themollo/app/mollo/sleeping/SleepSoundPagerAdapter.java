package themollo.app.mollo.sleeping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import themollo.app.mollo.sleeping.fragment.OceanWave;
import themollo.app.mollo.sleeping.fragment.RainyDay;
import themollo.app.mollo.sleeping.fragment.Train;
import themollo.app.mollo.sleeping.fragment.WaterBrook;

/**
 * Created by alex on 2018. 8. 31..
 */

public class SleepSoundPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_OF_FRAGMENT = 4;

    public SleepSoundPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RainyDay();
            case 1:
                return new WaterBrook();
            case 2:
                return new Train();
            case 3:
                return new OceanWave();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGMENT;
    }
}
