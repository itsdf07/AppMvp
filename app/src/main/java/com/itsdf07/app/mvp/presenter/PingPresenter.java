package com.itsdf07.app.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.itsdf07.app.mvp.contracts.PingContracts;
import com.itsdf07.app.mvp.model.PingModel;
import com.itsdf07.lib.mvp.presenter.BaseMvpPresenter;


/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/20
 */
public class PingPresenter extends BaseMvpPresenter<PingContracts.IPingView> implements PingContracts.IPingPresenter {
    PingContracts.IPingModel iPingModel;

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
                });
            }
        }).start();

    }
}
