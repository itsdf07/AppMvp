package com.itsdf07.app.mvp.deviceinfo;

import com.itsdf07.app.mvp.common.Tools2DeviceInfo;
import com.itsdf07.lib.mvp.presenter.BaseMvpPresenter;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/31
 */
public class MobileInfoPresenter extends BaseMvpPresenter<MobileInfoContracts.IMobileInfoView>
        implements MobileInfoContracts.IMobileInfoPresenter {
    MobileInfoModel mobileInfoModel;

    public MobileInfoPresenter(MobileInfoContracts.IMobileInfoView view) {
        super(view);
        mobileInfoModel = new MobileInfoModel();
    }

    @Override
    public String getScreenParams() {
        StringBuffer screenParams = new StringBuffer();
        screenParams.append("屏幕参数:\n");
        screenParams.append("分辨率(width * height):" + mobileInfoModel.getScreenWidth(getView().getSelfActivity()) + " * " + mobileInfoModel.getScreenHeight(getView().getSelfActivity()));
        screenParams.append("\n");
        screenParams.append("资源分辨率:");
        screenParams.append("\n");
        screenParams.append("Density:" + mobileInfoModel.getDensity(getView().getSelfActivity()));
        screenParams.append("\n");
        screenParams.append("ScaledDensity:" + mobileInfoModel.getScaledDensity(getView().getSelfActivity()));
        screenParams.append("\n");
        screenParams.append("DesityDpi:" + mobileInfoModel.getDesityDpi(getView().getSelfActivity()));
        screenParams.append("\n");
        screenParams.append("xdpi * ydpi:" + mobileInfoModel.getXdpi(getView().getSelfActivity()) + " * " + mobileInfoModel.getYdpi(getView().getSelfActivity()));
        return screenParams.toString();
    }

    @Override
    public String getDeviceParams() {
        StringBuffer deviceInfo = new StringBuffer();
        deviceInfo.append("硬件信息:\n");
        deviceInfo.append("品牌:" + Tools2DeviceInfo.getInstance().getBrand());
        deviceInfo.append("\n");
        deviceInfo.append("主板名称:" + Tools2DeviceInfo.getInstance().getBoard());
        deviceInfo.append("\n");
        deviceInfo.append("系统引导程序版本号:" + Tools2DeviceInfo.getInstance().getBootLoader());
        deviceInfo.append("\n");
        return deviceInfo.toString();
    }
}
