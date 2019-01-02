package themollo.app.mollo.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class CircularBarActivity extends AppCompatActivity {

    @BindView(R.id.seekArc)
    SeekArc seekArc;
    @BindView(R.id.tvRotation)
    TextView tvRotation;
    @BindView(R.id.ivGra)
    ImageView ivGra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_bar);
        ButterKnife.bind(this);

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                tvRotation.setText("" + i);
                int weight = i/2;
                seekArc.getLayoutParams().width = 525 + weight;
                seekArc.getLayoutParams().height = 525 + weight;
                seekArc.requestLayout();
                ivGra.getLayoutParams().height = 315 + weight;
                ivGra.getLayoutParams().width = 315 + weight;
                ivGra.requestLayout();
                Log.i("seekarc", "seekArc width : " + seekArc.getLayoutParams().width
                        + " seekArc height : " + seekArc.getLayoutParams().width
                        + " ivGra width : " + ivGra.getLayoutParams().width
                        + " ivGra height : " + ivGra.getLayoutParams().height);
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

    }
}
