package themollo.app.mollo.survey;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.home.HomeActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.FragUtilBasement;

public class SurveyResultPopup extends AppUtilBasement {

    @BindView(R.id.tvSleepScore)
    TextView tvSleepScore;

    @BindView(R.id.llConfirm)
    LinearLayout llConfirm;

    private int totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result_popup);
        butterBind();
        setButtonListener();

        SleepQuailtyClassifier s = new SleepQuailtyClassifier();
        totalScore = s.getTotalScore();

        tvSleepScore.setText(totalScore + "");
    }

    @Override
    public void setButtonListener() {
        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(HomeActivity.class);
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public class SleepQuailtyClassifier{

        public SleepQuailtyClassifier(){

        }

        private int getScoreFrom34(){
            float bedTime = toFloat(getSurveyData(BED_TIME));
            float deepSleepTime = toFloat(getSurveyData(DEEP_SLEEP_TIME));
            float percentage = deepSleepTime / bedTime;
            if(percentage >= 85) return 0;
            else if(percentage > 75 && percentage < 85) return 1;
            else if(percentage > 65 && percentage < 75) return 2;
            else if(percentage > 0 && percentage < 65) return 3;
            else return 0;
        }

        private int getScoreFrom5(){
            int whatDisturb = toInt(getSurveyData(WHAT_DISTURB));
            if(whatDisturb == 1 || whatDisturb == 0) return 0;
            else if(whatDisturb ==2 || whatDisturb == 3) return 1;
            else if(whatDisturb >= 4 && whatDisturb <=6) return 2;
            else if(whatDisturb > 7) return 3;
            else return 0;
        }

        private int getScoreFrom6(){
            return toInt(getSurveyData(DRUG_FOR_SLEEP));
        }

        private int getScoreFrom7(){
            return toInt(getSurveyData(ORDINARY_DAY_DISORDER));
        }

        public int getTotalScore(){
            float totalScore = getScoreFrom34() + getScoreFrom5() + getScoreFrom6() + getScoreFrom7();
            float percentage = (totalScore / 12) * 100;
            return (int) percentage;

        }

    }

}
