package com.itsdf07.app.mvp;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

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
    @Override
    public void onCreate() {
        super.onCreate();
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
        CrashReport.initCrashReport(getApplicationContext(), "b461faabba", false);
//        CrashReport.testJavaCrash();
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
