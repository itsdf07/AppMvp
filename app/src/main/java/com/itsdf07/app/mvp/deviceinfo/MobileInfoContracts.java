package com.itsdf07.app.mvp.deviceinfo;

import android.app.Activity;

import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/31
 */
public interface MobileInfoContracts {
    interface IMobileInfoView extends IBaseMvpView<Activity> {
    }

    interface IMobileInfoPresenter extends IBaseMvpPresenter {
        /**
         * 屏幕相关参数
         *
         * @return
         */
        String getScreenParams();

        /**
         * 设备硬件相关参数
         *
         * @return
         */
        String getDeviceParams();

    }

    interface IMobileInfoModel extends IBaseMvpModel {
    }
}
