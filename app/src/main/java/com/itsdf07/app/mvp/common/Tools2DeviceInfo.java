package com.itsdf07.app.mvp.common;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * @Description: 获取设备信息的工具，使用静态内部类的方式实现单例模式
 * @Author itsdf07
 * @Date 2019/10/28
 */
public class Tools2DeviceInfo {
    public static Tools2DeviceInfo getInstance() {
        return Tools2DeviceInfoHolder.instance;
    }

    private static class Tools2DeviceInfoHolder {
        private static final Tools2DeviceInfo instance = new Tools2DeviceInfo();
    }


    /**
     * @param context
     * @return DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=403.411, ydpi=403.041}
     */
    public DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取设备的分辨率:px<br/>
     * 其中扣掉了导航栏(返回键+Home键+Menu键)的高度(默认是126px)
     *
     * @param context
     * @return [widthPixels, heightPixels],如 [1080,1794]
     */
    public int[] getScreenPixels(Context context) {
        int[] pixels = new int[2];
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        pixels[0] = displayMetrics.widthPixels;
        pixels[1] = displayMetrics.heightPixels;
        return pixels;
    }

    /**
     * 屏幕的逻辑密度，是密度无关像素（dip）的缩放因子，
     * 160dpi是系统屏幕显示的基线，1dip = 1px，
     * 所以，在160dpi的屏幕上，density = 1， 而在一个120dpi屏幕上 density = 0.75
     *
     * @param context
     * @return 密度
     */
    public float getDensity(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.density;
    }

    /**
     * 每英寸的像素点数，屏幕密度的另一种表示。densityDpi = density * 160.
     *
     * @param context
     * @return
     */
    public int getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.densityDpi;
    }

    /**
     * 屏幕上字体显示的缩放因子，一般与density值相同，除非在程序运行中，用户根据喜好调整了显示字体的大小时，会有微小的增加。
     * 单位 sp 的 换算值: 一般用在设定字体大小中。
     * <p/>
     * 伸缩密度，图片、字体在不同分辨率上面运行，分辨率不一样导致设置的大小也就不一样。
     * <p/>
     * 一般字体大小设置为：
     * <p/>
     * DisplayMetrics dm= new DisplayMetrics();
     * getWindowManager().getDefaultDisplay().getMetrics(dm);
     * <p/>
     * pixelSize = (int)scaledPixelSize * dm.scaledDensity;
     * <p/>
     * 这样可以适配在多个分辨率上面
     *
     * @return 伸缩密度:单位 sp 的 换算值
     */
    public float getScaledDensity(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.scaledDensity;
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    public String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "UnKnown";
        } else {
            return deviceId;
        }
    }
    /* ------------------- BOARD 主板 ------------------- */

    /**
     * @return 主板名称
     */
    public String getBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * @return 系统引导程序版本号
     */
    public String getBootLoader() {
        return android.os.Build.BOOTLOADER;
    }

    /*------------- BRAND 运营商  ------------------------*/
    // phoneInfo += ", CPU_ABI2: " + android.os.Build.CPU_ABI2;

    /**
     * @return 品牌
     */
    public String getBrand() {
        return android.os.Build.BRAND;
    }

    public String getCPU_ABI() {
        return android.os.Build.CPU_ABI;
    }

    /*------------------ DEVICE 驱动 ----------------------*/

    /**
     * @return 设备驱动
     */
    public String getDevice() {
        return android.os.Build.DEVICE;
    }

    /*------------------ DISPLAY 显示 ----------------------*/

    /**
     * @return 一个构建ID字符串意味着显示给用户
     */
    public String getDisplay() {
        return android.os.Build.DISPLAY;
    }

    /*--------------------- 指纹 -----------------*/

    /**
     * @return 一个字符串, 唯一地标识此构建
     */
    public String getFingerprint() {
        return android.os.Build.FINGERPRINT;
    }

    /*-------------- HARDWARE 硬件  -----------------*/

    /**
     * 硬件的名称(从内核命令行或/ proc)。
     *
     * @return 硬件的名称
     */
    public String getHardware() {
        return android.os.Build.HARDWARE;
    }

    public String getHost() {
        return android.os.Build.HOST;
    }

    public String getId() {
        return android.os.Build.ID;
    }

    /*------------------  MANUFACTURER 生产厂家  --------------*/

    /**
     * @return 手机制造商
     */
    public String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /*------------------------MODEL 机型  ****************/

    /**
     * @return 手机型号
     */
    public String getModel() {
        return android.os.Build.MODEL;
    }

//    /**
//     * @return IMEI
//     */
//    public String getIMEI(Context context) {
//        String value = getTelephonyManager(context).getDeviceId();
//        return value;
//    }

    /**
     * @return 产品名称
     */
    public String getProduct() {
        return android.os.Build.PRODUCT;
    }

    public String getRadio() {
        return android.os.Build.RADIO;
    }

    public String getTags() {
        return android.os.Build.TAGS;
    }

    public long getTime() {
        return android.os.Build.TIME;
    }

    /**
     * @return 构建的类型, 如“用户”或“eng”。
     */
    public String getType() {
        return android.os.Build.TYPE;
    }

    public String getUser() {
        return android.os.Build.USER;
    }

    /*-------------------  VERSION.RELEASE 固件版本  -----------*/

    /**
     * @return 系统版本号
     */
    public String getVersionRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @return 开发代号 REL
     */
    public String getVersionCodeName() {
        return android.os.Build.VERSION.CODENAME;
    }

    /*----------------VERSION.SDK SDK版本  --------------------*/

    /**
     * @return SDK版本号
     */
    public String getVersionSDK() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * @return SDK版本号
     */
    public int getVersionSDK_INF() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /*-----------  VERSION.INCREMENTAL 基带版本 ---------------*/

    /**
     * @return 基带版本
     */
    public String getIncremental() {
        return android.os.Build.VERSION.INCREMENTAL;
    }

}
