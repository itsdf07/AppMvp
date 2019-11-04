package com.itsdf07.app.mvp;

import android.app.Application;
import android.text.TextUtils;

import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.alog.ALogLevel;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/24
 */
public class AppMvpApplication extends Application {
    //是否上报异常到Bugly平台
    private final boolean isCrashReport2Bugly = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ALog.init()
                .setTag("itsdf07-mvp")
                .setLogLevel(ALogLevel.FULL)
                .setLog2Local(false)
                .setShowThreadInfo(false);
        ALog.v("AppMvp项目启动了...");
        initCrashReport2Bugly();
    }

    /**
     * 初始化第三方异常上报：Bugly平台
     */
    private void initCrashReport2Bugly() {
// 获取当前包名
        String packageName = getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

//        第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//        1、输出详细的Bugly SDK的Log；
//        2、每一条Crash都会被立即上报；
//        3、自定义日志将会在Logcat中输出。
//        建议在测试阶段建议设置成true，发布时设置为false。
//        CrashReport.initCrashReport(getApplicationContext(), "b461faabba", isCrashReport2Bugly);
//        CrashReport.testJavaCrash();
        if (isCrashReport2Bugly) {
            ALog.v("开启Debug版本异常跟踪，可实时上报异常到Bugly平台，packageName：%s，processName：%s", packageName, processName);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
