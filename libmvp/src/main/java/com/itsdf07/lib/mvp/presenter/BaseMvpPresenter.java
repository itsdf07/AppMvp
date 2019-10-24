package com.itsdf07.lib.mvp.presenter;

import android.util.Log;

import com.itsdf07.lib.mvp.LibMvpConfig;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/21
 */
public class BaseMvpPresenter<V extends IBaseMvpView> implements IBaseMvpPresenter {
    public String TAG = this.getClass().getSimpleName();

    /**
     * 此处使用弱引用是因为，有时Activity关闭不一定会走onDestroy，所以这时使用弱引用可以及时回收IView
     */
    public Reference<V> view;

    public BaseMvpPresenter(V view) {
        attachView(view);
    }

    @Override
    public void detachView() {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "detachView->view:" + view);
        }

        if (view != null) {
            view.clear();
            view = null;
        }
    }


    /**
     * View 绑定
     *
     * @param view 视图界面
     */
    public void attachView(V view) {
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.v(TAG, "attachView->view:" + view);
        }
        this.view = new WeakReference(view);
    }

    /**
     * 主要用于判断IView的生命周期是否结束，防止出现内存泄露状况
     *
     * @return
     */
    protected boolean isViewAttach() {
        return view != null && view.get() != null;
    }

    protected V getView() {
        if (view != null) {
            return view.get();
        }
        if (LibMvpConfig.isShowLibMvpLog) {
            Log.e(TAG, "getView->view:" + view);
        }
        return null;
    }
}
