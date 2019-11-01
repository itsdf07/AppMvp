package com.itsdf07.app.mvp.deviceinfo;

import android.widget.TextView;

import com.itsdf07.app.mvp.R;
import com.itsdf07.app.mvp.common.Tools2DeviceInfo;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.BaseMvpActivity;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/28
 */
public class MobileInfoActivity extends BaseMvpActivity<MobileInfoPresenter> implements MobileInfoContracts.IMobileInfoView {
    private TextView tvMobileScreenParams;
    private TextView tvMobileParams;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mobileinfo;
    }

    @Override
    public void onBeforeView() {
    }

    @Override
    public void onInitView() {
        tvMobileScreenParams = findViewById(R.id.tv_mobileScreenParams);
        tvMobileParams = findViewById(R.id.tv_mobileParams);
    }

    @Override
    public void onAfterView() {

    }

    @Override
    public MobileInfoPresenter onInitPresenter() {
        return new MobileInfoPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        tvMobileScreenParams.setText(presenter.getScreenParams());
        tvMobileParams.setText(presenter.getDeviceParams());
        ALog.d("IMEI:%s", Tools2DeviceInfo.getInstance().getIMEI(this));
    }

    @Override
    public MobileInfoActivity getSelfActivity() {
        return this;
    }
}
