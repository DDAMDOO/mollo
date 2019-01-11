package themollo.app.mollo.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class DiffuserPopup extends AppUtilBasement {

    @BindView(R.id.tvOk)
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diffuser_popup);
        butterBind();
        setButtonListener();
    }

    @Override
    public void setButtonListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
