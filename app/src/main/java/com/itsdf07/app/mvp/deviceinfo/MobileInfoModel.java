package com.itsdf07.app.mvp.deviceinfo;

import android.content.Context;

import com.itsdf07.app.mvp.common.Tools2DeviceInfo;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/31
 */
public class MobileInfoModel implements MobileInfoContracts.IMobileInfoModel {

    public float getDensity(Context context) {
        return Tools2DeviceInfo.getInstance().getDensity(context);
    }

    public int getScreenWidth(Context context) {
        return Tools2DeviceInfo.getInstance().getScreenPixels(context)[0];
    }

    public int getScreenHeight(Context context) {
        return Tools2DeviceInfo.getInstance().getScreenPixels(context)[1];
    }

    public float getScaledDensity(Context context) {
        return Tools2DeviceInfo.getInstance().getScaledDensity(context);
    }

    /**
     * X轴方向上屏幕每英寸的物理像素数。
     *
     * @param context
     * @return
     */
    public float getXdpi(Context context) {
        return Tools2DeviceInfo.getInstance().getDisplayMetrics(context).xdpi;
    }

    /**
     * Y轴方向上屏幕每英寸的物理像素数。
     *
     * @param context
     * @return
     */
    public float getYdpi(Context context) {
        return Tools2DeviceInfo.getInstance().getDisplayMetrics(context).ydpi;
    }

    public float getDesityDpi(Context context) {
        return Tools2DeviceInfo.getInstance().getDensityDpi(context);
    }
}
