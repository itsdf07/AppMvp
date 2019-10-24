package com.itsdf07.app.mvp;


import android.app.Activity;
import android.view.View;

import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContracts.IMainView {

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
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public Activity getSelfActivity() {
        ALog.v("...");
        return this;
    }
}
