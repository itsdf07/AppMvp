package com.itsdf07.app.mvp.model;

import com.google.gson.Gson;
import com.itsdf07.app.mvp.bean.RespPingHostBean;
import com.itsdf07.app.mvp.contracts.PingContracts;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.net.OkBaseBean;
import com.itsdf07.lib.net.OkHttp3CallbackImpl;
import com.itsdf07.lib.net.OkHttp3Utils;
import com.itsdf07.lib.net.RequestInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public class PingModel implements PingContracts.IPingModel {
    private static final String TAG = "PingModel";

    public interface IHostsCallback {
        void hostsResultCallback(List<RespPingHostBean.DatasBean> datas);
    }

    public interface IPingResultCallback {
        void pingResultCallback(String pingResult);

        void pingResultErr(String errResult);

        void pingOver(String host);
    }

    @Override
    public void getHosts(String group, final IHostsCallback callback) {
        String url = "http://192.168.2.130:8087/itsdf07/ping/hosts";
        JSONObject root = new JSONObject();
        try {
            root.put("group", group);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttp3Utils.getInstance().postAsyn2Data(url, root.toString(), new OkHttp3CallbackImpl() {
            @Override
            public void onStart(RequestInfo requestInfo) {

            }

            @Override
            public void onSuccess(OkBaseBean okBaseBean) {
                RespPingHostBean pingHostBean = new Gson().fromJson(okBaseBean.getEncrptyResult(), RespPingHostBean.class);
                ALog.dTag(TAG, okBaseBean.getEncrptyResult());
                if (null != callback) {
                    callback.hostsResultCallback(pingHostBean.getDatas());
                }
            }

            @Override
            public void onFailed(OkBaseBean bean) {

            }

            @Override
            public void onFinish() {

            }
        }, true);
    }

    //------------------------ping成功-------------------------------
//
//PING 118.178.32.100 (118.178.32.100) 64(92) bytes of data.
//72 bytes from 118.178.32.100: icmp_seq=1 ttl=89 time=101 ms
//72 bytes from 118.178.32.100: icmp_seq=2 ttl=89 time=100 ms
//72 bytes from 118.178.32.100: icmp_seq=3 ttl=89 time=100 ms
//72 bytes from 118.178.32.100: icmp_seq=4 ttl=89 time=97.7 ms
//
//--- 118.178.32.100 ping statistics ---
//4 packets transmitted, 4 received, 0% packet loss, time 3002ms
//rtt min/avg/max/mdev = 97.709/100.014/101.684/1.479 ms
//
//------------------------ping失败-------------------------------
//
//PING www.a.shifen.com (14.215.177.39) 64(92) bytes of data.
//
//--- www.a.shifen.com ping statistics ---
//4 packets transmitted, 0 received, 100% packet loss, time 3000ms
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
                sleep(1 * 500);
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

        if (null != callback) {
            callback.pingOver(host);
        }
    }

}
