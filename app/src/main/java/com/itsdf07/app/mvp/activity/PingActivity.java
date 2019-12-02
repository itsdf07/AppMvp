package com.itsdf07.app.mvp.activity;

import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.itsdf07.app.mvp.R;
import com.itsdf07.app.mvp.contracts.PingContracts;
import com.itsdf07.app.mvp.presenter.PingPresenter;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.BaseMvpActivity;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public class PingActivity extends BaseMvpActivity<PingPresenter> implements PingContracts.IPingView {
    private EditText etHost;
    private EditText etPackageCount;
    private EditText etPackageSize;
    private EditText etPackageDelaytime;
    private EditText etHostGroup;
    private TextView tvPingResultInfo;
    private TextView tvPingCountDown;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ALog.dTag(TAG, "keyCode:%s", keyCode);
        return true;
    }

    @Override
    public PingPresenter onInitPresenter() {
        return new PingPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        autoPing();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ping;
    }

    @Override
    public void onBeforeView() {

    }

    @Override
    public void onInitView() {
        etHost = findViewById(R.id.et_host);
        etPackageCount = findViewById(R.id.et_package_count);
        etPackageSize = findViewById(R.id.et_package_size);
        etPackageDelaytime = findViewById(R.id.et_package_delaytime);
        etHostGroup = findViewById(R.id.et_host_group);
        tvPingCountDown = findViewById(R.id.tv_ping_count_down);
        tvPingResultInfo = findViewById(R.id.tv_pingresult);
        tvPingResultInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.btn_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPingResultInfo.setText("");
                int packageCount = Integer.parseInt(etPackageCount.getText().toString().trim());
                int packageSize = Integer.parseInt(etPackageSize.getText().toString().trim());
                int packageDelaytime = Integer.parseInt(etPackageDelaytime.getText().toString().trim());
                presenter.onPing(etHost.getText().toString().trim(), packageCount, packageSize, packageDelaytime);
            }
        });
        findViewById(R.id.btn_ping_host).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPingResultInfo.setText("");
                int packageCount = Integer.parseInt(etPackageCount.getText().toString().trim());
                int packageSize = Integer.parseInt(etPackageSize.getText().toString().trim());
                int packageDelaytime = Integer.parseInt(etPackageDelaytime.getText().toString().trim());
                presenter.onPingHost(etHostGroup.getText().toString().trim(), packageCount, packageSize, packageDelaytime);
            }
        });
    }

    @Override
    public void onAfterView() {

    }

    @Override
    public PingActivity getSelfActivity() {
        return this;
    }

    @Override
    public void updateInfo(String pingResult) {
        ALog.dTag(TAG, "pingResult:%s", pingResult);
        tvPingResultInfo.append("\n" + pingResult);
    }

    @Override
    public void updatePingCountDown(int countDown) {
        ALog.dTag(TAG, "Ping倒计时:%s S", countDown);
        if (countDown <= 0) {
            tvPingCountDown.setText("Ping过程中，请等候...");
        } else {
            tvPingCountDown.setText("Ping倒计时:" + countDown + "s");
        }
    }

    private void autoPing() {
        tvPingResultInfo.setText("");
        updatePingCountDown(0);
        int packageCount = Integer.parseInt(etPackageCount.getText().toString().trim());
        int packageSize = Integer.parseInt(etPackageSize.getText().toString().trim());
        int packageDelaytime = Integer.parseInt(etPackageDelaytime.getText().toString().trim());
        presenter.onPingHost("", packageCount, packageSize, packageDelaytime);
    }

}
