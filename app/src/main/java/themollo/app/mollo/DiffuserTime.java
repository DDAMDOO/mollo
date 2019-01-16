package themollo.app.mollo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class DiffuserTime extends AppUtilBasement {

    @BindView(R.id.tvOk)
    TextView tvOk;
    @BindView(R.id.seekBar1)
    SeekBar sb;
    @BindView(R.id.sbValue)
    TextView sbVal;

    private static String VALUE = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diffuser_time);
        butterBind();
        setButtonListener();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VALUE = (progress)*20+"";
                sbVal.setText(VALUE);
                Log.d("TAG", "" + VALUE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setButtonListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiffuserTime.this, TimerActivity.class);
                intent.putExtra("countdown_val",sbVal.getText().toString());
                Log.d("ZXC",sbVal+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
