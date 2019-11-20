package com.itsdf07.app.mvp.contracts;

import android.app.Activity;

import com.itsdf07.app.mvp.model.PingModel;
import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public interface PingContracts {
    interface IPingView extends IBaseMvpView<Activity> {
        /**
         * UI更新
         *
         * @param pingResult
         */
        void updateInfo(String pingResult);
    }

    interface IPingPresenter extends IBaseMvpPresenter {
        /**
         * ping功能
         *
         * @param host         主机，如域名、IP
         * @param packageCount 发送数据包的数目
         * @param packageSize  发送数据包的大小(byte)
         * @param delayTime    发送数据包的间隔时间(s)
         */
        void onPing(String host, int packageCount, int packageSize, int delayTime);
    }

    interface IPingModel extends IBaseMvpModel {
        /**
         * ping功能
         *
         * @param host         主机，如域名、IP
         * @param packageCount 发送数据包的数目
         * @param packageSize  发送数据包的大小(byte)
         * @param delayTime    发送数据包的间隔时间(s)
         * @param callback
         */
        void ping(String host, int packageCount, int packageSize, int delayTime, PingModel.IPingResultCallback callback);
    }
}
