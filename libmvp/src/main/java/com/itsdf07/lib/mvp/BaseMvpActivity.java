package com.itsdf07.lib.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/21
 */
public abstract class BaseMvpActivity<P extends IBaseMvpPresenter> extends BaseActivity
        implements IBaseMvpView<Activity> {
    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onInitPresenter();
        onAfterPresenter();
    }

    @Override
    protected void onDestroy() {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "onDestroy->");
        }
        /**
         * 在生命周期结束时，将presenter与view之间的联系断开，防止出现内存泄露
         */
        if (null != presenter) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 实例化Presenter对象，会自动在Activity中持有它
     * 备注:如果需要在初始化Presenter之前执行某些事情，可以在onAfterView中进行
     *
     * @return
     */
    public abstract P onInitPresenter();

    public abstract void onAfterPresenter();


    @Override
    public void showLoading() {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "showLoading->...");
        }
    }

    @Override
    public void showLoading(String content) {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "showLoading->content:" + content);
        }
        if (null == content) {
            Log.w(TAG, "showLoading->content的显示内容为null，将为您显示默认内容...");
            showLoading();
            return;
        }
    }

    @Override
    public void hideLoading() {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "hideLoading->...");
        }
    }

    @Override
    public void showToast(String content) {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "showToast->content:" + content);
        }
        if (null == content) {
            Log.w(TAG, "showToast->content的显示内容为null，将主动为您置为\"\"");
            content = "";
        }
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
