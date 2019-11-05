package com.itsdf07.app.mvp.nf877.ble;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itsdf07.app.mvp.R;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.bt.ble.bean.BLEChannelSetting;
import com.itsdf07.lib.bt.ble.bean.BLEPublicSetting;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public class BLEActivity extends BaseMvpActivity<BLEPresenter> implements BLEContracts.IBLEView, AdapterView.OnItemSelectedListener {
    @BindView(R.id.tv_connect_status)
    TextView tvConnectStatus;
    @BindView(R.id.tv_ble_info)
    TextView tvBleInfo;
    @BindView(R.id.sp_gps)
    Spinner spGps;
    @BindView(R.id.sp_bluetooth_status)
    Spinner spBluetoothStatus;
    @BindView(R.id.sp_squelch1)
    Spinner spSquelch1;
    @BindView(R.id.sp_voice_level)
    Spinner spVoiceLevel;
    @BindView(R.id.sp_voice_delay)
    Spinner spVoiceDelay;
    @BindView(R.id.sp_scan_type)
    Spinner spScanType;
    @BindView(R.id.sp_display_model)
    Spinner spDisplayModel;
    @BindView(R.id.sp_beep)
    Spinner spBeep;
    @BindView(R.id.sp_voice2send)
    Spinner spVoice2send;
    @BindView(R.id.sp_tot_timeout)
    Spinner spTotTimeout;
    @BindView(R.id.sp_display_time)
    Spinner spDisplayTime;
    @BindView(R.id.sp_power_model)
    Spinner spPowerModel;
    @BindView(R.id.sp_xdxz)
    Spinner spXdxz;
    @BindView(R.id.tx_minus)
    TextView txMinus;
    @BindView(R.id.tx_plus)
    TextView txPlus;
    @BindView(R.id.et_tx)
    EditText etTx;
    @BindView(R.id.sp_ctcss2Encode)
    Spinner spCtcss2Encode;
    @BindView(R.id.et_rx)
    EditText etRx;
    @BindView(R.id.sp_ctcss2Decode)
    Spinner spCtcss2Decode;
    @BindView(R.id.sp_scan)
    Spinner spScan;
    @BindView(R.id.sp_bandwidth)
    Spinner spBandwidth;
    @BindView(R.id.sp_transmitpower)
    Spinner spTransmitpower;
    @BindView(R.id.sp_tsxl)
    Spinner spTsxl;
    @BindView(R.id.btn_readHz)
    Button btnReadHz;
    @BindView(R.id.btn_writeHz)
    Button btnWriteHz;
    private BLEScanResult bleScanResult;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDisConnectedBLE();
            presenter.detachView();
        }
    }

    @Override
    public BLEPresenter onInitPresenter() {
        return new BLEPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        presenter.setBLEScanResult(bleScanResult);
        presenter.onConnectedBLE();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ble_channel;
    }

    @Override
    public void onBeforeView() {
        ButterKnife.bind(this);
        bleScanResult = getIntent().getParcelableExtra(BLEPresenter.EXTRA_BLEDEVICE);
        if (null == bleScanResult) {
            ALog.dTag(TAG, "蓝牙设备信息获取失败");
            this.finish();
        }
        ALog.dTag(TAG, "BLE名称:%s,Mac:%s", bleScanResult.getBluetoothDevice().getName(), bleScanResult.getBluetoothDevice().getAddress());
    }

    @Override
    public void onInitView() {
        spGps.setOnItemSelectedListener(this);
        spBluetoothStatus.setOnItemSelectedListener(this);
        spSquelch1.setOnItemSelectedListener(this);
        spVoiceLevel.setOnItemSelectedListener(this);
        spVoiceDelay.setOnItemSelectedListener(this);
        spScanType.setOnItemSelectedListener(this);
        spDisplayModel.setOnItemSelectedListener(this);
        spBeep.setOnItemSelectedListener(this);
        spVoice2send.setOnItemSelectedListener(this);
        spTotTimeout.setOnItemSelectedListener(this);
        spDisplayTime.setOnItemSelectedListener(this);
        spPowerModel.setOnItemSelectedListener(this);
        spXdxz.setOnItemSelectedListener(this);
        spCtcss2Decode.setOnItemSelectedListener(this);
        spCtcss2Encode.setOnItemSelectedListener(this);
        spScan.setOnItemSelectedListener(this);
        spBandwidth.setOnItemSelectedListener(this);
        spTransmitpower.setOnItemSelectedListener(this);
    }

    @Override
    public void onAfterView() {
        tvBleInfo.setText(getString(R.string.string_public_information) + "　" +
                bleScanResult.getBluetoothDevice().getName() + "->" + bleScanResult.getBluetoothDevice().getAddress());
        updateBLEConnectStatus(BLEPresenter.BLE_STATUS_DISCONNECTED);
        etRx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setTx2Receive(s.toString());
            }
        });
        etTx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setTx2Send(s.toString());
            }
        });
    }

    @Override
    public Activity getSelfActivity() {
        return this;
    }


    @OnClick({R.id.btn_readHz, R.id.btn_writeHz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_readHz:
                break;
            case R.id.btn_writeHz:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ALog.eTag(TAG, "position:%s,id:%s", position, id);
//        if (null == bleChannelSettingHashMap.get(0)) {
//            Log.w(TAG, "blePublicSetting is null");
//            return;
//        }
//        switch (parent.getId()) {
//            case R.id.sp_gps:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setGps(spGps.getSelectedItemPosition());
//                break;
//            case R.id.sp_bluetooth_status:
////                Log.d("SPPPP", "position:" + position + ",value:" + spBluetoothStatus.getSelectedItem().toString());
////                spBluetoothStatus.setSelection(position,true);
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setBluetoothStatus(spBluetoothStatus.getSelectedItemPosition());
//                break;
//            case R.id.sp_squelch1:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setSquelch1(spSquelch1.getSelectedItemPosition());
//                break;
//            case R.id.sp_voice_level:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setVoiceLevel(spVoiceLevel.getSelectedItemPosition());
//                break;
//            case R.id.sp_voice_delay:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setVoiceDelay((spVoiceDelay.getSelectedItemPosition()));
//                break;
//            case R.id.sp_scan_type:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setScanType(spSscanType.getSelectedItemPosition());
//                break;
//            case R.id.sp_display_model:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setDisplayModel(spDisplayModel.getSelectedItemPosition());
//                break;
//            case R.id.sp_beep:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setBeep(spBeep.getSelectedItemPosition());
//                break;
//            case R.id.sp_voice2send:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setVoice2Send(spVoice2Send.getSelectedItemPosition());
//                break;
//            case R.id.sp_tot_timeout:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setTotTimeOut(spTotTimeOut.getSelectedItemPosition());
//                break;
//            case R.id.sp_display_time:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setDisplayTime(spDisplayTime.getSelectedItemPosition());
//                break;
//            case R.id.sp_power_model:
//                ((BLEPublicSetting) bleChannelSettingHashMap.get(0)).setPowerMode(spPowerMode.getSelectedItemPosition());
//                break;
//            case R.id.sp_xdxz:
//                updataChannel(((BLEChannelSetting) bleChannelSettingHashMap.get(position + 1)));
//                break;
//            case R.id.sp_ctcss2Decode:
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setCtcss2Decode(spCtcss2Decode.getSelectedItem().toString());
//                break;
//            case R.id.sp_ctcss2Encode:
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setCtcss2Encode(spCtcss2Encode.getSelectedItem().toString());
//                break;
//            case R.id.sp_sacn:
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setScan(position);
//                break;
//            case R.id.sp_bandwidth:
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setBandwidth(position);
//                break;
//            case R.id.sp_transmitpower:
//                ((BLEChannelSetting) bleChannelSettingHashMap.get(spXdxz.getSelectedItemPosition() + 1)).setTransmitPower(position);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //EidtText内容改变后的监听 value是控件ID，callback对应改变前，中，后的事件
    @OnTextChanged(value = R.id.et_rx, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable s) {
        ALog.dTag(TAG, "s:%s", s.toString());
    }

    @Override
    public void updateBLEConnectStatus(int status) {
        switch (status) {
            case BLEPresenter.BLE_STATUS_CONNECTED:
                tvConnectStatus.setText("已连接");
                break;
            case BLEPresenter.BLE_STATUS_CONNECTING:
                tvConnectStatus.setText("连接中...");
                break;
            case BLEPresenter.BLE_STATUS_DISCONNECTED:
            default:
                tvConnectStatus.setText("未连接");
                break;
        }

    }
}
