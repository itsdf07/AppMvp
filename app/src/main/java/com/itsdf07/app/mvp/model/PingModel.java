package com.itsdf07.app.mvp.model;

import com.itsdf07.app.mvp.contracts.PingContracts;
import com.itsdf07.lib.alog.ALog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public class PingModel implements PingContracts.IPingModel {
    private static final String TAG = "PingModel";

    public interface IPingResultCallback {
        void pingResultCallback(String pingResult);

        void pingResultErr(String errResult);
    }

    @Override
    public void ping(String host, int packageCount, int packageSize, int delayTime, IPingResultCallback callback) {
        String ping = "ping -c " + packageCount + " -s " + packageSize + " -i " + delayTime + " " + host;
        ALog.dTag(TAG, "ping:%s", ping);
        String result = "";
        Process process = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;
        try {
            process = Runtime.getRuntime().exec(ping);
            InputStream in = process.getInputStream();
            successReader = new BufferedReader(new InputStreamReader(in));
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String lineStr;
            int resultStatus = process.waitFor();
            ALog.iTag(TAG, "resultStatus:%s", resultStatus == 0 ? "ping成功" : "ping失败");
            while ((lineStr = successReader.readLine()) != null) {
                result = result + lineStr + "\n";
                ALog.iTag(TAG, "succ result:%s", lineStr);
                if (null != callback) {
                    callback.pingResultCallback(lineStr);
                }
                sleep(1 * 1000);
            }
            while ((lineStr = errorReader.readLine()) != null) {
                ALog.eTag(TAG, "err result:%s", lineStr);
                if (null != callback) {
                    callback.pingResultErr(lineStr);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (successReader != null) {
                    successReader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
    }

}
