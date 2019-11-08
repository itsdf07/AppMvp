package com.itsdf07.app.mvp.nf877.ble;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itsdf07.app.mvp.R;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.bt.ble.bean.BLEChannelSetting;
import com.itsdf07.lib.bt.ble.bean.BLEPublicSetting;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;
import com.itsdf07.lib.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.et_freq)
    EditText etFreq;
    @BindView(R.id.sp_ctcss)
    Spinner spCtcss;
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
        updatePublic(presenter.getBLEPublicSetting());
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
        spCtcss.setOnItemSelectedListener(this);
        spScan.setOnItemSelectedListener(this);
        spBandwidth.setOnItemSelectedListener(this);
        spTransmitpower.setOnItemSelectedListener(this);
    }

    @Override
    public void onAfterView() {
        tvBleInfo.setText(getString(R.string.string_public_information) + "　" +
                bleScanResult.getBluetoothDevice().getName() + "->" + bleScanResult.getBluetoothDevice().getAddress());
        updateBLEConnectStatus(BLEPresenter.BLE_STATUS_DISCONNECTED);
        etFreq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1).setTxFreq(s.toString());
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
                presenter.readData();
                break;
            case R.id.btn_writeHz:
                presenter.writeData();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ALog.eTag(TAG, "position:%s,id:%s", position, id);
        if (null == presenter.getBLEPublicSetting()) {
            Log.w(TAG, "blePublicSetting is null");
            return;
        }
        switch (parent.getId()) {
            case R.id.sp_gps:
                presenter.getBLEPublicSetting().setGps(spGps.getSelectedItemPosition());
                break;
            case R.id.sp_bluetooth_status:
                presenter.getBLEPublicSetting().setBluetoothStatus(spBluetoothStatus.getSelectedItemPosition());
                break;
            case R.id.sp_squelch1:
                presenter.getBLEPublicSetting().setSquelch1(spSquelch1.getSelectedItemPosition());
                break;
            case R.id.sp_voice_level:
                presenter.getBLEPublicSetting().setVoiceLevel(spVoiceLevel.getSelectedItemPosition());
                break;
            case R.id.sp_voice_delay:
                presenter.getBLEPublicSetting().setVoiceDelay((spVoiceDelay.getSelectedItemPosition()));
                break;
            case R.id.sp_scan_type:
                presenter.getBLEPublicSetting().setScanType(spScanType.getSelectedItemPosition());
                break;
            case R.id.sp_display_model:
                presenter.getBLEPublicSetting().setDisplayModel(spDisplayModel.getSelectedItemPosition());
                break;
            case R.id.sp_beep:
                presenter.getBLEPublicSetting().setBeep(spBeep.getSelectedItemPosition());
                break;
            case R.id.sp_voice2send:
                presenter.getBLEPublicSetting().setVoice2Send(spVoice2send.getSelectedItemPosition());
                break;
            case R.id.sp_tot_timeout:
                presenter.getBLEPublicSetting().setTotTimeOut(spTotTimeout.getSelectedItemPosition());
                break;
            case R.id.sp_display_time:
                presenter.getBLEPublicSetting().setDisplayTime(spDisplayTime.getSelectedItemPosition());
                break;
            case R.id.sp_power_model:
                presenter.getBLEPublicSetting().setPowerMode(spPowerModel.getSelectedItemPosition());
                break;
            case R.id.sp_xdxz:
                updateChannel(presenter.getBLEChannelSetting(position + 1));
                break;
            case R.id.sp_ctcss:
                presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1).setCtcss(spCtcss.getSelectedItem().toString());
                break;
            case R.id.sp_scan:
                presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1).setScan(position);
                break;
            case R.id.sp_bandwidth:
                presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1).setBandwidth(position);
                break;
            case R.id.sp_transmitpower:
                presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1).setTransmitPower(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateChannel(BLEChannelSetting bleChannelSetting) {
        etFreq.setText(bleChannelSetting.getTxFreq());
        spTransmitpower.setSelection(bleChannelSetting.getTransmitPower(), true);
        spBandwidth.setSelection(bleChannelSetting.getBandwidth(), true);
        spScan.setSelection(bleChannelSetting.getScan(), true);
        spCtcss.setSelection(getIndex(bleChannelSetting.getCtcss()), true);
    }

    private void updatePublic(BLEPublicSetting blePublicSetting) {
        spGps.setSelection(blePublicSetting.getGps(), true);
        spBluetoothStatus.setSelection(blePublicSetting.getBluetoothStatus(), true);
        spSquelch1.setSelection(blePublicSetting.getSquelch1(), true);
        spVoiceLevel.setSelection(blePublicSetting.getVoiceLevel(), true);
        spVoiceDelay.setSelection(blePublicSetting.getVoiceDelay(), true);
        spScanType.setSelection(blePublicSetting.getScanType(), true);
        spDisplayModel.setSelection(blePublicSetting.getDisplayModel(), true);
        spBeep.setSelection(blePublicSetting.getBeep(), true);
        spVoice2send.setSelection(blePublicSetting.getVoice2Send(), true);
        spVoiceDelay.setSelection(blePublicSetting.getVoiceDelay(), true);
        spDisplayTime.setSelection(blePublicSetting.getDisplayTime(), true);
        spPowerModel.setSelection(blePublicSetting.getPowerMode(), true);
    }

    private int getIndex(String value) {
        String[] arrays = getResources().getStringArray(R.array.array_hz_ctcdcs);
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].equals(value)) {
                return i;
            }
        }
        return 0;
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

    @Override
    public void updateBLEOperateBtn(boolean operating) {
        btnReadHz.setEnabled(!operating);
        btnWriteHz.setEnabled(!operating);
        updatePublic(presenter.getBLEPublicSetting());
        updateChannel(presenter.getBLEChannelSetting(spXdxz.getSelectedItemPosition() + 1));
    }
}
