package themollo.app.mollo.survey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

import themollo.app.mollo.survey.survey_fragment.Survey_p1;
import themollo.app.mollo.survey.survey_fragment.Survey_p2;
import themollo.app.mollo.survey.survey_fragment.Survey_p3;
import themollo.app.mollo.survey.survey_fragment.Survey_p4;
import themollo.app.mollo.survey.survey_fragment.Survey_p5;
import themollo.app.mollo.survey.survey_fragment.Survey_p6;
import themollo.app.mollo.survey.survey_fragment.Survey_p7;

/**
 * Created by alex on 2018. 7. 16..
 */

public class SurveyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_OF_FRAGMENT = 7;

    public SurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Survey_p1();
            case 1:
                return new Survey_p2();
            case 2:
                return new Survey_p3();
            case 3:
                return new Survey_p4();
            case 4:
                return new Survey_p5();
            case 5:
                return new Survey_p6();
            case 6:
                return new Survey_p7();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGMENT;
    }

    public XmlPullParser getFragmentByPosition(int pos) throws XmlPullParserException {
        XmlPullParserFactory factory  = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader("Survey_p" + (pos+1)));
        return xpp;
    }

}
