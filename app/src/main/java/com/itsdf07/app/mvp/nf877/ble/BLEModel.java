package com.itsdf07.app.mvp.nf877.ble;

import android.util.Log;

import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.bt.ble.bean.BLEChannelSetting;
import com.itsdf07.lib.bt.ble.bean.BLEPublicSetting;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/5
 */
public class BLEModel implements BLEContracts.IBLEModel {
    private static final String TAG = "BLEModel";
    /**
     * 32信道对应的独立信道协议
     */
    private HashMap<Integer, Object> bleChannelSettingHashMap = new HashMap<>();

    public BLEModel(int num) {
        initBleChannelSettingHashMap(num);
    }


    private void initBleChannelSettingHashMap(int num) {

        BLEPublicSetting blePublicSetting = new BLEPublicSetting();
        blePublicSetting.setGps(0);//GPS默认关
        blePublicSetting.setBluetoothStatus(0);//蓝牙默认关
        blePublicSetting.setSquelch1(5);//静噪等级默认5级
        blePublicSetting.setVoiceLevel(0);//声控等级默认关
        blePublicSetting.setVoiceDelay(0);//声控延时默认0.5s
        blePublicSetting.setScanType(1);//扫描模式默认载波扫描(CO,TO为时间)
        blePublicSetting.setDisplayModel(1);//显示模式默认彩色
        blePublicSetting.setBeep(0);
        blePublicSetting.setVoice2Send(0);//发射提示音默认关
        blePublicSetting.setTotTimeOut(12);//发射限时默认180s
        blePublicSetting.setDisplayTime(0);//屏保时间默认关
        blePublicSetting.setPowerMode(1);//省电模式默认开
        bleChannelSettingHashMap.put(0, blePublicSetting);
        for (int i = 1; i <= num; i++) {
            BLEChannelSetting bleChannelSetting = new BLEChannelSetting();
            bleChannelSetting.setChannelNum(i);
            bleChannelSetting.setTxFreq("");
            bleChannelSetting.setCtcss("-1");
            bleChannelSetting.setTransmitPower(1);
            bleChannelSetting.setScan(0);
            bleChannelSetting.setBandwidth(0);
            bleChannelSettingHashMap.put(i, bleChannelSetting);
        }
    }


    @Override
    public BLEPublicSetting getBLEPublicSetting() {
        return (BLEPublicSetting) bleChannelSettingHashMap.get(0);
    }

    @Override
    public BLEChannelSetting getBLEChannelSetting(int position) {
        return (BLEChannelSetting) bleChannelSettingHashMap.get(position);
    }

    @Override
    public ArrayList<byte[][]> handshakeProtocol() {
        //索引值奇数为App应发送的握手数据给BLE设备，偶数为APP应接收BLE的握手响应数据
        ArrayList<byte[][]> writeHandshakeProtocol = new ArrayList<>();
        byte[][] protocol_step1_2handshake = new byte[2][8];
        //发送协议头:49 69 4E 48 53 47 30 4E
        protocol_step1_2handshake[0][0] = (byte) 0x49;
        protocol_step1_2handshake[0][1] = (byte) 0x69;
        protocol_step1_2handshake[0][2] = (byte) 0x4E;
        protocol_step1_2handshake[0][3] = (byte) 0x48;
        protocol_step1_2handshake[0][4] = (byte) 0x53;
        protocol_step1_2handshake[0][5] = (byte) 0x47;
        protocol_step1_2handshake[0][6] = (byte) 0x30;
        protocol_step1_2handshake[0][7] = (byte) 0x4E;
        //接收响应:06
        protocol_step1_2handshake[1][0] = (byte) 0x06;


        byte[][] protocol_step2_2handshake = new byte[2][8];
        //发送握手:02
        protocol_step2_2handshake[0][0] = (byte) 0x02;
        //接收握手响应:50 33 31 30 37 00 00 00
        protocol_step2_2handshake[1][0] = (byte) 0x50;
        protocol_step2_2handshake[1][1] = (byte) 0x33;
        protocol_step2_2handshake[1][2] = (byte) 0x31;
        protocol_step2_2handshake[1][3] = (byte) 0x30;
        protocol_step2_2handshake[1][4] = (byte) 0x37;
        protocol_step2_2handshake[1][5] = (byte) 0x00;
        protocol_step2_2handshake[1][6] = (byte) 0x00;
        protocol_step2_2handshake[1][7] = (byte) 0x00;

        byte[][] protocol_step3_2handshake = new byte[2][1];
        //确认握手:06
        protocol_step3_2handshake[0][0] = (byte) 0x06;
        //答复握手:06
        protocol_step3_2handshake[1][0] = (byte) 0x06;

        writeHandshakeProtocol.add(protocol_step1_2handshake);
        writeHandshakeProtocol.add(protocol_step2_2handshake);
        writeHandshakeProtocol.add(protocol_step3_2handshake);
        return writeHandshakeProtocol;
    }


    @Override
    public byte[] getBLEPublicDataPackage(BLEPublicSetting blePublicSetting) {
        byte[] datas = new byte[20];
        datas[0] = 0x57;
        datas[1] = 0x0A;
        datas[2] = 0x00;
        datas[3] = 0x10;

        datas[4] = (byte) blePublicSetting.getGps();
        datas[5] = (byte) blePublicSetting.getBluetoothStatus();
        datas[6] = (byte) blePublicSetting.getSquelch1();
        datas[7] = (byte) 0xFF;
        datas[8] = (byte) blePublicSetting.getVoiceLevel();
        datas[9] = (byte) blePublicSetting.getVoiceDelay();
        datas[10] = (byte) blePublicSetting.getScanType();
        datas[11] = (byte) blePublicSetting.getDisplayModel();
        datas[12] = (byte) blePublicSetting.getBeep();
        datas[13] = (byte) blePublicSetting.getVoice2Send();
        datas[14] = (byte) blePublicSetting.getTotTimeOut();
        datas[15] = (byte) blePublicSetting.getDisplayTime();
        datas[16] = (byte) blePublicSetting.getPowerMode();
        datas[17] = (byte) 0xFF;
        datas[18] = (byte) 0xFF;
        datas[19] = (byte) 0xFF;
        return datas;
    }

    @Override
    public byte[] getChannelDataPackage(BLEChannelSetting bleChannelSetting) {
        byte[] recHz = tx2Hex(bleChannelSetting.getTxFreq());
        byte[] sendHz = tx2Hex(bleChannelSetting.getTxFreq());
        byte[] decodeCtcDcs = converCtcDcs2DEC(bleChannelSetting.getCtcss());
        byte[] encodeCtcDcs = converCtcDcs2DEC(bleChannelSetting.getCtcss());
        byte[] datas = new byte[20];
        short address = (short) ((bleChannelSetting.getChannelNum() - 1) * 16);
        datas[0] = 0x57;
        datas[1] = (byte) (address >> 8);
        datas[2] = (byte) address;
        datas[3] = 0x10;

        datas[4] = recHz[0];
        datas[5] = recHz[1];
        datas[6] = recHz[2];
        datas[7] = recHz[3];
        datas[8] = sendHz[0];
        datas[9] = sendHz[1];
        datas[10] = sendHz[2];
        datas[11] = sendHz[3];
        datas[12] = encodeCtcDcs[0];
        datas[13] = encodeCtcDcs[1];
        datas[14] = decodeCtcDcs[0];
        datas[15] = decodeCtcDcs[1];
        datas[16] = (byte) ((byte) bleChannelSetting.getTransmitPower() + (((byte) bleChannelSetting.getBandwidth()) << 1));
        datas[17] = (byte) (((byte) bleChannelSetting.getScan()) << 1);
        datas[18] = (byte) 0xFF;
        datas[19] = (byte) 0xFF;
        return datas;
    }


    /**
     * 信道频率数据
     * 把462.0125的转成发送格式为：50 12 20 46
     *
     * @param freq 462.0125
     * @return
     */
    private byte[] tx2Hex(String freq) {
        try {
            byte[] freq2Byte = new byte[4];
            if ("".equals(freq.trim()) || freq == null) {
                freq2Byte[0] = (byte) 0xFF;
                freq2Byte[1] = (byte) 0xFF;
                freq2Byte[2] = (byte) 0xFF;
                freq2Byte[3] = (byte) 0xFF;
            } else {
                //TODO 这里可以考虑做下频段的范围限制
                long l = new Double(Double.valueOf(Double.valueOf(freq).doubleValue() * 100000.0D).doubleValue()).longValue();
                ALog.dTag("tx2Hex", "param:%s,l:%s", freq, l);
                freq = l + "";
                if (freq.length() != 8) {
                    ALog.eTag(TAG, "信道频率有误,param:%s", freq);
                    return null;
                }
                freq2Byte[0] = (byte) Integer.parseInt(freq.substring(6, 8), 16);
                freq2Byte[1] = (byte) Integer.parseInt(freq.substring(4, 6), 16);
                freq2Byte[2] = (byte) Integer.parseInt(freq.substring(2, 4), 16);
                freq2Byte[3] = (byte) Integer.parseInt(freq.substring(0, 2), 16);
            }
            return freq2Byte;
        } catch (Exception e) {
            ALog.eTag(TAG, "e:%s", e);
        }
        return null;
    }

    /**
     * CTC/DCS 编解码转换成DEC数据
     * 如编解码 69.3 转成DEC数据：9306
     *
     * @param value 如 69.3
     * @return 9306
     */
    private byte[] converCtcDcs2DEC(String value) {
        ALog.dTag(TAG, "value:%s", value);
        byte[] dec = new byte[2];
        if (value.contains("I")) {//0xA800
            value = value.replace("D", "").replace("I", "");
            short decValue = (short) (Integer.valueOf(value, 8) + 0xA800);
            dec[0] = (byte) decValue;
            dec[1] = (byte) (decValue >> 8);
        } else if (value.contains("N")) {//0x2800
            value = value.replace("D", "").replace("N", "");
            short decValue = (short) (Integer.valueOf(value, 8) + 0x2800);
            dec[0] = (byte) decValue;
            dec[1] = (byte) (decValue >> 8);
        } else if (value.contains(".")) {
//            if (!(value + "").matches("[0-9].")) {
//                dec[0] = (byte) 0xFF;
//                dec[1] = (byte) 0xFF;
//                return dec;
//            }
            short decValue = (short) (Double.valueOf(value) * 10);
            dec[0] = (byte) decValue;
            dec[1] = (byte) (decValue >> 8);
        } else {
            dec[0] = (byte) 0xFF;
            dec[1] = (byte) 0xFF;
        }
        return dec;
    }


    /**
     * 10进制转16进制
     *
     * @param demical 十进制值
     * @return
     */
    @Override
    public String demical2Hex(int demical) {
        ALog.dTag(TAG, "demical:%s", demical);
        if (!(demical + "").matches("[0-9]*")) {
            return "";
        }
        String hexadecimal = Integer.toHexString(demical);
        return hexadecimal;
    }
}
