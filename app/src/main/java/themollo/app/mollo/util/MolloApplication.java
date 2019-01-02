package themollo.app.mollo.util;

import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.kakao.auth.KakaoSDK;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.tsengvn.typekit.Typekit;

import themollo.app.mollo.login.sns_login.kakao.KakaoSDKAdapter;

/**
 * Created by alex on 2018. 7. 9..
 */

public class MolloApplication extends MultiDexApplication {

    private static MolloApplication instance = null;
    private static final String RoundEB = "font/NanumSquareRoundEB.ttf";
    private static final String Regular = "font/NanumSquareRegular.ttf";
    private static final String OTFB = "font/NanumSquareOTFBold.otf";
    private static final String OTFEB = "font/NanumSquareOTFExtraBold.otf";
    private static final String RoundR = "font/NanumSquareRoundR.ttf";
    private static final String RoundL = "font/NanumSquareRoundL.ttf";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        MolloApplication.instance = this;
        RxPaparazzo.register(this);
        KakaoSDK.init(new KakaoSDKAdapter());

        Typekit.getInstance()
                .addNormal(getFontType(OTFB))
                .addBold(getFontType(RoundEB));

        Typekit.getInstance().add("OTFEB",getFontType(OTFEB));
        Typekit.getInstance().add("OTFB", getFontType(OTFB));
        Typekit.getInstance().add("RoundR", getFontType(RoundR));
        Typekit.getInstance().add("RoundL", getFontType(RoundL));




    }

    private Typeface getFontType(String font){
        return Typekit.createFromAsset(this, font);
    }

    public static MolloApplication getInstance(){
        return instance;
    }
}
