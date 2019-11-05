package com.itsdf07.app.mvp.nf877.ble;

import android.widget.Toast;

import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.bt.ble.bean.BLEChannelSetting;
import com.itsdf07.lib.bt.ble.bean.BLEPublicSetting;
import com.itsdf07.lib.bt.ble.client.core.OKBLEDevice;
import com.itsdf07.lib.bt.ble.client.core.OKBLEDeviceImp;
import com.itsdf07.lib.bt.ble.client.core.OKBLEDeviceListener;
import com.itsdf07.lib.bt.ble.client.core.OKBLEOperation;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.presenter.BaseMvpPresenter;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public class BLEPresenter extends BaseMvpPresenter<BLEContracts.IBLEView> implements OKBLEDeviceListener, BLEContracts.IBLEPresenter {
    public static final String EXTRA_BLEDEVICE = BLEPresenter.class.getName() + ".EXTRA_BLEDEVICE";
    public static final String UUIDWRITE = "0000ffe3-0000-1000-8000-00805f9b34fb";
    public static final String UUIDNOTIFY = "0000ffe2-0000-1000-8000-00805f9b34fb";

    public static final int BLE_STATUS_DISCONNECTED = 0;
    public static final int BLE_STATUS_CONNECTING = 1;
    public static final int BLE_STATUS_CONNECTED = 2;

    private final int MAX_CHANNEL = 32;
    /**
     * 16信道对应的独立信道协议
     */
    private HashMap<Integer, Object> bleChannelSettingHashMap = new HashMap<>();

    private BLEScanResult bleScanResult;

    private OKBLEDevice okbleDevice;

    public BLEPresenter(BLEContracts.IBLEView view) {
        super(view);
        initBleChannelSettingHashMap();
//        okbleDevice = new OKBLEDeviceImp(getView().getSelfActivity(), bleScanResult);
        okbleDevice = new OKBLEDeviceImp(getView().getSelfActivity());
        okbleDevice.addDeviceListener(this);
//        okbleDevice.connect(true);
    }

    @Override
    public void setBLEScanResult(BLEScanResult bleScanResult) {
        this.bleScanResult = bleScanResult;
    }

    @Override
    public BLEScanResult getBLEScanResult() {
        return bleScanResult;
    }

    @Override
    public void onConnectedBLE() {
        okbleDevice.setBleScanResult(bleScanResult);
        okbleDevice.connect(true);
    }

    @Override
    public void onDisConnectedBLE() {
        if (okbleDevice != null) {
            okbleDevice.disConnect(false);
            okbleDevice.remove();
        }
    }

    private void initBleChannelSettingHashMap() {

        BLEPublicSetting blePublicSetting = new BLEPublicSetting();
        bleChannelSettingHashMap.put(0, blePublicSetting);
        for (int i = 1; i <= MAX_CHANNEL; i++) {
            BLEChannelSetting bleChannelSetting = new BLEChannelSetting();
            bleChannelSetting.setChannelNum(i);
            bleChannelSetting.setTx2Send("400.12500");
            bleChannelSetting.setTx2Receive("400.12500");
            bleChannelSetting.setCtcss2Decode("67.0");
            bleChannelSetting.setCtcss2Encode("67.0");
            bleChannelSetting.setTransmitPower(1);
            bleChannelSetting.setScan(0);
            bleChannelSetting.setBandwidth(1);
            bleChannelSettingHashMap.put(i, bleChannelSetting);
        }
    }

    @Override
    public void onConnected(String deviceTAG) {
        ALog.eTag(TAG, "deviceTAG:%s", deviceTAG);
        getView().getSelfActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().updateBLEConnectStatus(BLE_STATUS_CONNECTED);
                Toast.makeText(getView().getSelfActivity(), "通知打开中...", Toast.LENGTH_SHORT).show();
            }
        });

        final OKBLEOperation.OperationType[] operationType = new OKBLEOperation.OperationType[1];

        okbleDevice.addNotifyOrIndicateOperation(UUIDNOTIFY, true, new OKBLEOperation.NotifyOrIndicateOperationListener() {
            @Override
            public void onNotifyOrIndicateComplete() {
                ALog.eTag(TAG, "通知已打开");
                getView().getSelfActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getView().getSelfActivity(), "通知已打开" + operationType[0].name(), Toast.LENGTH_SHORT).show();
                        if (operationType[0] == OKBLEOperation.OperationType.OperationType_Enable_Indicate) {
                        } else if (operationType[0] == OKBLEOperation.OperationType.OperationType_Enable_Notify) {
                        }
                    }
                });
            }

            @Override
            public void onFail(int code, final String errMsg) {
                ALog.eTag(TAG, "code:%s,errMsg:%s", code, errMsg);
                getView().getSelfActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void onExecuteSuccess(OKBLEOperation.OperationType type) {
                ALog.eTag(TAG, "type:%s", type.name());
                operationType[0] = type;
            }
        });
    }

    @Override
    public void onDisconnected(String deviceTAG) {
        ALog.eTag(TAG, "deviceTAG:%s", deviceTAG);
        getView().getSelfActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().updateBLEConnectStatus(BLE_STATUS_DISCONNECTED);
            }
        });
    }

    @Override
    public void onReadBattery(String deviceTAG, int battery) {
        ALog.eTag(TAG, "deviceTAG:%s,battery:%s", deviceTAG, battery);
    }

    @Override
    public void onReceivedValue(String deviceTAG, String uuid, byte[] value) {
//        ALog.eTag(TAG, "onReceivedValue->isDataWriting:%s,handshakeNum:%s,deviceTAG:%s,uuid:%s,value:%s",
//                isDataWriting, handshakeNum, deviceTAG, uuid, Arrays.toString(value));
    }

    @Override
    public void onWriteValue(String deviceTAG, String uuid, byte[] value, boolean success) {
        ALog.eTag(TAG, "deviceTAG:%s,uuid:%s,success:%s,value:%s", deviceTAG, uuid, success, Arrays.toString(value));
    }

    @Override
    public void onReadValue(String deviceTAG, String uuid, byte[] value, boolean success) {
        ALog.eTag(TAG, "deviceTAG:%s,uuid:%s,success:%s,value:%s", deviceTAG, uuid, success, Arrays.toString(value));
    }

    @Override
    public void onNotifyOrIndicateComplete(String deviceTAG, String uuid, boolean enable, boolean success) {
        ALog.eTag(TAG, "deviceTAG:%s,uuid:%s,success:%s,enable:%s", deviceTAG, uuid, success, enable);
    }
}
