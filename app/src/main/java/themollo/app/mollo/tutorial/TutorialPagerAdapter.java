package themollo.app.mollo.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import themollo.app.mollo.tutorial.tutorial_fragment.Tutorial_p1;
import themollo.app.mollo.tutorial.tutorial_fragment.Tutorial_p2;
import themollo.app.mollo.tutorial.tutorial_fragment.Tutorial_p3;
import themollo.app.mollo.tutorial.tutorial_fragment.Tutorial_p4;
import themollo.app.mollo.tutorial.tutorial_fragment.Tutorial_p5;

/**
 * Created by alex on 2018. 7. 23..
 */

public class TutorialPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_OF_FRAGMENT = 5;

    public TutorialPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tutorial_p1();
            case 1:
                return new Tutorial_p2();
            case 2:
                return new Tutorial_p3();
            case 3:
                return new Tutorial_p4();
            case 4:
                return new Tutorial_p5();
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGMENT;
    }
}
