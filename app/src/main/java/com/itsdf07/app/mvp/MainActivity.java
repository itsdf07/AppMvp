package com.itsdf07.app.mvp;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.itsdf07.app.mvp.activity.PingActivity;
import com.itsdf07.app.mvp.deviceinfo.MobileInfoActivity;
import com.itsdf07.app.mvp.nf877.ble.BLEScanActivity;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContracts.IMainView, View.OnClickListener {
    private TextView tvInfo;

    @Override
    public int getLayoutId() {
        ALog.v("...");
        return R.layout.activity_main;
    }

    @Override
    public void onBeforeView() {
        ALog.v("...");
    }

    String result = "";

    @Override
    public void onInitView() {
        ALog.v("...");
        tvInfo = findViewById(R.id.tv_info);
        findViewById(R.id.btn_2_deviceInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MobileInfoActivity.class));
            }
        });
        findViewById(R.id.btn_2_nf877).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BLEScanActivity.class));
            }
        });
        findViewById(R.id.btn_ping_host).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PingActivity.class));
            }
        });
    }

    @Override
    public void onAfterView() {
        ALog.v("...");
    }

    @Override
    public MainPresenter onInitPresenter() {
        ALog.v("...");
        return new MainPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        ALog.v("...");
    }

    @Override
    public MainActivity getSelfActivity() {
        ALog.v("...");
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_2_deviceInfo:

                break;
        }
    }
}
