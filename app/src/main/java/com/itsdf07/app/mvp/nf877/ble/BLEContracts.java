package com.itsdf07.app.mvp.nf877.ble;

import android.app.Activity;

import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public interface BLEContracts {
    interface IBLEView extends IBaseMvpView<Activity> {
        /**
         * 更新蓝牙连接状态
         *
         * @param status
         */
        void updateBLEConnectStatus(int status);
    }

    interface IBLEPresenter extends IBaseMvpPresenter {
        void setBLEScanResult(BLEScanResult bleScanResult);

        BLEScanResult getBLEScanResult();

        void onConnectedBLE();

        void onDisConnectedBLE();
    }

    interface IBLEModel extends IBaseMvpModel {
    }
}
