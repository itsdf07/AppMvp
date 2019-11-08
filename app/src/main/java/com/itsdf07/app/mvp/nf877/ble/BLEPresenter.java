package com.itsdf07.app.mvp.nf877.ble;

import android.util.Log;
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

    /**
     * 当前支持的频道数
     */
    private final int MAX_CHANNEL = 32;
    /**
     * 当前是否正在写数据:true-正在写入数据
     */
    private boolean isDataWriting = false;

    /**
     * 当前握手次数：握手次数为3次
     */
    private int handshakeNum = 0;

    /**
     * 发送的数据包个数:0表示公共协议数据包，后面的所以对应的是频道数据包
     */
    private int packageDataIndex = 0;

    BLEContracts.IBLEModel ibleModel;

    private BLEScanResult bleScanResult;

    private OKBLEDevice okbleDevice;


    public BLEPresenter(BLEContracts.IBLEView view) {
        super(view);
        ibleModel = new BLEModel(MAX_CHANNEL);
        okbleDevice = new OKBLEDeviceImp(getView().getSelfActivity());
        okbleDevice.addDeviceListener(this);
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

    @Override
    public void writeData() {
        getView().updateBLEOperateBtn(true);
        isDataWriting = true;
        handshakeNum = 1;
        sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0]);
    }

    @Override
    public void readData() {
        getView().updateBLEOperateBtn(true);
        handshakeNum = 1;
        sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0]);
    }

    @Override
    public BLEPublicSetting getBLEPublicSetting() {
        return ibleModel.getBLEPublicSetting();
    }

    @Override
    public BLEChannelSetting getBLEChannelSetting(int position) {
        return ibleModel.getBLEChannelSetting(position);
    }

    private void sendData(String uuid, byte data) {
        byte[] datas = new byte[1];
        datas[0] = data;
        sendData(uuid, datas);
    }

    private void sendData(String uuid, byte[] data) {
        ALog.dTag(TAG, "uuid:%s,data:%s", uuid, Arrays.toString(data));
        okbleDevice.addWriteOperation(uuid, data, new OKBLEOperation.WriteOperationListener() {
            @Override
            public void onWriteValue(byte[] value) {
                ALog.eTag(TAG, "value:%s", Arrays.toString(value));

            }

            @Override
            public void onFail(int code, String errMsg) {
                ALog.eTag(TAG, "code:%s,errMsg:%s", code, errMsg);

            }

            @Override
            public void onExecuteSuccess(OKBLEOperation.OperationType type) {
                ALog.eTag(TAG, "type:%s", type);
            }
        });
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

    byte[] readPublie = new byte[4];

    @Override
    public void onReceivedValue(String deviceTAG, String uuid, byte[] value) {
        ALog.eTag(TAG, "onReceivedValue->isDataWriting:%s,handshakeNum:%s,deviceTAG:%s,uuid:%s,value:%s",
                isDataWriting, handshakeNum, deviceTAG, uuid, Arrays.toString(value));
        if (isDataWriting) {//写入数据
            switch (handshakeNum) {
                case 1:
                    if (value[0] == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][0]) {
                        handshakeNum++;
                        sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0][0]);
                    } else {

                    }
                    break;

                case 2:
                    if (value.length == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1].length) {
                        boolean isMatch = true;
                        for (int i = 0; i < value.length; i++) {
                            if (value[i] != ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][i]) {
                                isMatch = false;
                                break;
                            }
                        }
                        if (isMatch) {
                            handshakeNum++;
                            ALog.e(TAG, "onReceivedValue->握手成功....");
                            // TODO 发送 (byte) 0x06
                            sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0]);
                        }
                    } else {

                    }
                    break;
                case 3:
                    if (value[0] == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][0]) {
                        handshakeNum = 0;//握手结束，复位握手次数
                        //TODO 开始发送第一个数据包:公共协议
                        packageDataIndex++;
                        sendData(UUIDWRITE, ibleModel.getBLEPublicDataPackage(getBLEPublicSetting()));
                    }
                    break;
                default:
                    if (value[0] == (byte) 0x06) {
                        //TODO 开始发送第N+1个数据包:设置数据
                        if (packageDataIndex > 32) {
                            sendData(UUIDWRITE, (byte) 0x45);
                            packageDataIndex = 0;
                            isDataWriting = false;

                            getView().getSelfActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getView().getSelfActivity(), "数据写入完成", Toast.LENGTH_SHORT).show();
                                    getView().updateBLEOperateBtn(false);
                                }
                            });
                        } else {
                            sendData(UUIDWRITE, ibleModel.getChannelDataPackage(getBLEChannelSetting(packageDataIndex)));
                            packageDataIndex++;
                        }
                    }
                    break;
            }

        } else {//读取数据
            switch (handshakeNum) {
                case 1:
                    if (value[0] == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][0]) {
                        handshakeNum++;
                        sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0][0]);
                    } else {

                    }
                    break;
                case 2:
                    if (value.length == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1].length) {
                        boolean isMatch = true;
                        for (int i = 0; i < value.length; i++) {
                            if (value[i] != ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][i]) {
                                isMatch = false;
                                break;
                            }
                        }
                        if (isMatch) {
                            handshakeNum++;
                            ALog.e(TAG, "onReceivedValue->握手成功....");
                            // TODO 发送 (byte) 0x06
                            sendData(UUIDWRITE, ibleModel.handshakeProtocol().get(handshakeNum - 1)[0]);
                        }
                    } else {

                    }
                    break;
                case 3:
                    if (value[0] == ibleModel.handshakeProtocol().get(handshakeNum - 1)[1][0]) {
                        handshakeNum = 0;//握手结束，复位握手次数
                        //TODO 开始读取第一个数据包:公共协议
                        packageDataIndex = 0;
                        readPublie[0] = 0x52;
                        readPublie[1] = 0x0A;
                        readPublie[2] = 0x00;
                        readPublie[3] = 0x10;
                        sendData(UUIDWRITE, readPublie);
                    }
                    break;
                default:
                    if (value[0] == (byte) 0x57
                            && value[1] == readPublie[1]
                            && value[2] == readPublie[2]
                            && value[3] == readPublie[3]) {

                        //TODO 对BLEPublicSetting进行复制
                        if (packageDataIndex == 0) {
                            getBLEPublicSetting().setGps(value[4]);
                            getBLEPublicSetting().setBluetoothStatus(value[5]);
                            getBLEPublicSetting().setSquelch1(value[6]);
                            getBLEPublicSetting().setVoiceLevel(value[8]);
                            getBLEPublicSetting().setVoiceDelay(value[9]);
                            getBLEPublicSetting().setScanType(value[10]);
                            getBLEPublicSetting().setDisplayModel(value[11]);
                            getBLEPublicSetting().setBeep(value[12]);
                            getBLEPublicSetting().setVoice2Send(value[13]);
                            getBLEPublicSetting().setTotTimeOut(value[14]);
                            getBLEPublicSetting().setDisplayTime(value[15]);
                            getBLEPublicSetting().setPowerMode(value[16]);
                            ALog.dTag(TAG, "BLEPublicSetting:%s", getBLEPublicSetting().toString());
                        } else {
                            Log.e("readData", "接收到频道协议数据，第" + packageDataIndex + "个数据接收成功");
                            int results = value[4] + (value[5] << 8) + (value[6] << 16) + (value[7] << 24);
                            ALog.dTag(TAG, "results:%s", results);
                            String freq = ibleModel.demical2Hex(results);
                            String freqResult = freq.substring(0, 3) + "." + freq.substring(3);
                            getBLEChannelSetting(packageDataIndex).setTxFreq(freqResult);
//                            results = value[8] + (value[9] << 8) + (value[10] << 16) + (value[11] << 24);
                            short ctcss = (short) ((short) (value[14] & 0xFF) + (((short) ((value[15] & 0xFF)) << 8)));
                            if (ctcss > 0x2800) {
                                String octalString = Integer.toOctalString(ctcss - 0x2800);
                                if (octalString.length() < 3) {
                                    getBLEChannelSetting(packageDataIndex).setCtcss("D0" + octalString + "N");
                                } else {
                                    getBLEChannelSetting(packageDataIndex).setCtcss("D" + octalString + "N");
                                }
                            } else if (ctcss > 0) {
                                getBLEChannelSetting(packageDataIndex).setCtcss(ctcss / 10 + "." + ctcss % 10);
                            } else {
                                getBLEChannelSetting(packageDataIndex).setCtcss("OFF/关闭");
                            }
//                            ctcss = (short) ((short) (value[12] & 0xFF) + (((short) (value[13] & 0xFF) << 8)));

                            getBLEChannelSetting(packageDataIndex).setTransmitPower(value[16] % 2);
                            getBLEChannelSetting(packageDataIndex).setBandwidth(value[16] >> 1);
                            getBLEChannelSetting(packageDataIndex).setScan(value[17] >> 1);
                            ALog.dTag(TAG, "BLEChannelSetting:%s", getBLEChannelSetting(packageDataIndex).toString());
                        }
                        ALog.dTag(TAG, "通知下位机第" + packageDataIndex + "个数据包接收成功");
                        sendData(UUIDWRITE, (byte) 0x06);
                    } else if (value[0] == (byte) 0x06) {
                        Log.e("readData", "下位机知道了上位机第" + packageDataIndex + "个数据包接收成功");
                        if (packageDataIndex < 32) {
                            packageDataIndex = packageDataIndex + 1;
                            short address = (short) ((packageDataIndex - 1) * 16);
                            readPublie[1] = (byte) (address >> 8);
                            readPublie[2] = (byte) address;
                            Log.e(TAG, "开始请求第" + packageDataIndex + "个数据");
                            sendData(UUIDWRITE, readPublie);
                        } else {
                            Log.e(TAG, "请求结束。共请求了" + packageDataIndex + "个数据包");
                            packageDataIndex = 0;
                            sendData(UUIDWRITE, (byte) 0x45);
                            isDataWriting = false;
                            getView().getSelfActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getView().getSelfActivity(), "数据读取完成", Toast.LENGTH_SHORT).show();
                                    getView().updateBLEOperateBtn(false);
                                }
                            });
                        }

                    }
                    break;
            }
        }
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
