package com.itsdf07.app.mvp.nf877.ble;

import android.app.Activity;

import com.itsdf07.lib.bt.ble.bean.BLEChannelSetting;
import com.itsdf07.lib.bt.ble.bean.BLEPublicSetting;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

import java.util.ArrayList;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public interface BLEContracts<T> {
    interface IBLEView extends IBaseMvpView<Activity> {
        /**
         * 更新蓝牙连接状态
         *
         * @param status
         */
        void updateBLEConnectStatus(int status);

        void updateBLEOperateBtn(boolean operating);
    }

    interface IBLEPresenter extends IBaseMvpPresenter {
        void setBLEScanResult(BLEScanResult bleScanResult);

        BLEScanResult getBLEScanResult();

        void onConnectedBLE();

        void onDisConnectedBLE();

        void writeData();

        void readData();

        BLEPublicSetting getBLEPublicSetting();

        BLEChannelSetting getBLEChannelSetting(int position);
    }

    interface IBLEModel extends IBaseMvpModel {
        BLEPublicSetting getBLEPublicSetting();

        BLEChannelSetting getBLEChannelSetting(int position);


        /**
         * 设备握手协议头
         *
         * @return
         */
        ArrayList<byte[][]> handshakeProtocol();


        /**
         * 获取公共协议写入数据包
         *
         * @param blePublicSetting
         * @return
         */
        byte[] getBLEPublicDataPackage(BLEPublicSetting blePublicSetting);

        /**
         * 获取频道协议写入数据包
         *
         * @param bleChannelSetting
         * @return
         */
        byte[] getChannelDataPackage(BLEChannelSetting bleChannelSetting);

        String demical2Hex(int demical);
    }
}
