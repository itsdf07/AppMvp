package com.itsdf07.app.mvp;


import android.app.Activity;
import android.util.Log;

import com.itsdf07.lib.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContracts.IMainView {

    @Override
    public int getLayoutId() {
        Log.d(TAG, "getLayoutId->");
        return R.layout.activity_main;
    }

    @Override
    public void onBeforeView() {
        Log.d(TAG, "onBeforeView->");
    }

    @Override
    public void onInitView() {
        Log.d(TAG, "onInitView->");
    }

    @Override
    public void onAfterView() {
        Log.d(TAG, "onAfterView->");
    }

    @Override
    public MainPresenter onInitPresenter() {
        Log.d(TAG, "onInitPresenter->");
        return new MainPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        Log.d(TAG, "onAfterPresenter->");
    }

    @Override
    public Activity getSelfActivity() {
        Log.d(TAG, "getSelfActivity->");
        return this;
    }
}
