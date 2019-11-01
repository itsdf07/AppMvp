package com.itsdf07.app.mvp;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.itsdf07.app.mvp.deviceinfo.MobileInfoActivity;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContracts.IMainView, View.OnClickListener {

    @Override
    public int getLayoutId() {
        ALog.v("...");
        return R.layout.activity_main;
    }

    @Override
    public void onBeforeView() {
        ALog.v("...");
    }

    @Override
    public void onInitView() {
        ALog.v("...");
        findViewById(R.id.btn_2_deviceInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MobileInfoActivity.class));
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
