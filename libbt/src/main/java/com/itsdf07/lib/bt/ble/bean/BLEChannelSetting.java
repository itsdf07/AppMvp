package com.itsdf07.lib.bt.ble.bean;

/**
 * @Description ：信道协议
 * @Author itsdf07
 * @Time 2017/12/8
 */

public class BLEChannelSetting {

    /**
     * 信道的值+1
     */
    private long id = 0L;
    /**
     * 信道
     */
    private int channelNum = 1;
    /**
     * 频率
     */
    private String txFreq = "";//462.0125

    /**
     * 亚音频
     */
    private String ctcss = "OFF/关闭";

    /**
     * 添加扫描
     */
    private int scan = 0;
    /**
     * [通信] 频带宽度
     */
    private int bandwidth = 0;
    /**
     * 发射功率
     */
    private int transmitPower = 0;
    /**
     * 繁忙锁定
     */
    private int busyLock = 0;
    /**
     * 扰频
     */
    private int scramble = 0;
    /**
     * 压扩
     */
    private int companding = 0;
    /**
     * 特殊信令
     */
    private int specialSignaling = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public String getTxFreq() {
        return txFreq;
    }

    public void setTxFreq(String txFreq) {
        if (txFreq.toUpperCase().equals("FFF.FFFF")) {
            txFreq = "";
        }
        this.txFreq = txFreq;
    }


    public String getCtcss() {
        return ctcss;
    }

    public void setCtcss(String ctcss) {
        this.ctcss = ctcss;
    }


    public int getScan() {
        return scan;
    }

    public void setScan(int scan) {
        this.scan = scan;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getTransmitPower() {
        return transmitPower;
    }

    public void setTransmitPower(int transmitPower) {
        this.transmitPower = transmitPower;
    }

    public int getBusyLock() {
        return busyLock;
    }

    public void setBusyLock(int busyLock) {
        this.busyLock = busyLock;
    }

    public int getScramble() {
        return scramble;
    }

    public void setScramble(int scramble) {
        this.scramble = scramble;
    }

    public int getCompanding() {
        return companding;
    }

    public void setCompanding(int companding) {
        this.companding = companding;
    }

    public int getSpecialSignaling() {
        return specialSignaling;
    }

    public void setSpecialSignaling(int specialSignaling) {
        this.specialSignaling = specialSignaling;
    }

    @Override
    public String toString() {
        return "信道:" + channelNum + ",发射频率:" + txFreq + ",CTCSS:" + ctcss
                + ",是否添加扫描:" + scan + ",带宽:" + bandwidth + ",发射功率:" + transmitPower + ",是否繁忙锁定:" + busyLock
                + ",是否扰频:" + scramble + ",是否压扩:" + companding + ",是否特殊信令:" + specialSignaling;
    }
}
