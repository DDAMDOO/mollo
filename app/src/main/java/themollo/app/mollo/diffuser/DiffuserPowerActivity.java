package themollo.app.mollo.diffuser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class DiffuserPowerActivity extends AppUtilBasement {

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
        setContentView(R.layout.activity_diffuser_power);
        butterBind();
        setButtonListener();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VALUE = progress+"";
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
                finish();
                Toast.makeText(DiffuserPowerActivity.this,VALUE
                        +"단계",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}