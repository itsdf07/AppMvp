package com.itsdf07.app.mvp.nf877.ble;

import android.app.Activity;

import com.itsdf07.lib.bt.ble.LinkedHashMap;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public interface BLEScanContracts {
    interface IBLEScanView extends IBaseMvpView<Activity> {
        /**
         * 刷新整个适配器内容
         */
        void notifyUpdata2Adapter();

        /**
         * 针对列表的某一项进行刷新
         *
         * @param index
         */
        void notifyUpdata2Item(int index);
    }

    interface IBLEScanPresenter extends IBaseMvpPresenter {
        LinkedHashMap<String, BLEScanResult> getBLEs();

        void startScan();

        void stopScan();
    }

    interface IBLEScanModel extends IBaseMvpModel {
    }
}
