package com.itsdf07.app.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.itsdf07.app.mvp.bean.RespPingHostBean;
import com.itsdf07.app.mvp.contracts.PingContracts;
import com.itsdf07.app.mvp.model.PingModel;
import com.itsdf07.lib.alog.ALog;
import com.itsdf07.lib.mvp.presenter.BaseMvpPresenter;

import java.util.HashMap;
import java.util.List;


/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public class PingPresenter extends BaseMvpPresenter<PingContracts.IPingView> implements PingContracts.IPingPresenter {
    PingContracts.IPingModel iPingModel;

    private HashMap<String, HashMap<String, String>> hostMaps = new HashMap<>();

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String pingResult = (String) msg.obj;
            getView().updateInfo(pingResult);
        }
    };

    public PingPresenter(PingContracts.IPingView view) {
        super(view);
        iPingModel = new PingModel();
    }

    @Override
    public void onPing(final String host, final int packageCount, final int packageSize, final int delayTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                iPingModel.ping(host, packageCount, packageSize, delayTime, new PingModel.IPingResultCallback() {
                    @Override
                    public void pingResultCallback(final String pingResult) {
                        Message msg = mMainHandler.obtainMessage();
                        msg.obj = pingResult;
                        msg.sendToTarget();
                    }

                    @Override
                    public void pingResultErr(final String errResult) {
                        Message msg = mMainHandler.obtainMessage();
                        msg.obj = errResult;
                        msg.sendToTarget();
                    }

                    @Override
                    public void pingOver(String host) {

                    }
                });
            }
        }).start();

    }

    @Override
    public void onPingHost(String group, final int packageCount, final int packageSize, final int delayTime) {
        if (group == null) {
            group = "";
        }
        hostMaps.clear();
        iPingModel.getHosts(group.trim(), new PingModel.IHostsCallback() {
            @Override
            public void hostsResultCallback(List<RespPingHostBean.DatasBean> datas) {
                if (datas == null || datas.isEmpty()) {
                    return;
                }
                ping(packageCount, packageSize, delayTime, datas, 0);
            }
        });
    }

    @Override
    public void onAddPingResults(HashMap<String, HashMap<String, String>> hostMaps) {
        iPingModel.addPingResults(hostMaps);
    }

    private void ping(final int packageCount, final int packageSize,
                      final int delayTime, final List<RespPingHostBean.DatasBean> datas, final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String host = datas.get(index).getHost();
                iPingModel.ping(host, packageCount, packageSize, delayTime, new PingModel.IPingResultCallback() {
                    @Override
                    public void pingResultCallback(final String pingResult) {
                        if (pingResult.startsWith("PING")) {
                            String nextHost = pingResult.substring("PING ".length(), pingResult.indexOf(" ("));
                            String ip = pingResult.substring(pingResult.indexOf("(") + 1, pingResult.indexOf(")"));
                            ALog.eTag("HOST_IP", "nextHost,%s,ip:%s", nextHost, ip);
                            if (datas.get(index).getAddresses().isEmpty()) {
                                HashMap<String, String> maps = new HashMap<>();
                                maps.put("nextHost", nextHost);
                                maps.put("ip", ip);
                                maps.put("host", host);
                                hostMaps.put(host, maps);
                            } else {
                                boolean isNew = true;
                                for (RespPingHostBean.DatasBean.AddressesBean bean : datas.get(index).getAddresses()) {
                                    if (bean == null) {
                                        continue;
                                    }
                                    ALog.eTag("HOST_IP", "prNextHost,%s,prIp:%s", bean.getPrNextHost(), bean.getPrIp());
                                    if ((!TextUtils.isEmpty(bean.getPrIp()) & bean.getPrIp().equals(ip))
                                            && (!TextUtils.isEmpty(bean.getPrNextHost()) && bean.getPrNextHost().equals(nextHost))) {
                                        isNew = false;
                                        break;
                                    }
                                }
                                ALog.eTag("HOST_IP", "isNew:%s", isNew);
                                if (isNew) {
                                    HashMap<String, String> maps = new HashMap<>();
                                    maps.put("nextHost", nextHost);
                                    maps.put("ip", ip);
                                    maps.put("host", host);
                                    hostMaps.put(host, maps);
                                }
                            }

                        }

                        Message msg = mMainHandler.obtainMessage();
                        msg.obj = pingResult;
                        msg.sendToTarget();
                    }

                    @Override
                    public void pingResultErr(final String errResult) {
                        Message msg = mMainHandler.obtainMessage();
                        msg.obj = errResult;
                        msg.sendToTarget();
                    }

                    @Override
                    public void pingOver(String host) {
                        final int nextIndex = index + 1;
                        if (nextIndex >= datas.size()) {
                            if (!hostMaps.isEmpty()) {
                                onAddPingResults(hostMaps);
                            }
                            return;
                        }
                        ping(packageCount, packageSize, delayTime, datas, nextIndex);
                    }
                });
            }
        }).start();
    }
}
