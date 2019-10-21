package com.itsdf07.app.mvp;

import android.app.Activity;

import com.itsdf07.lib.mvp.model.IBaseMvpModel;
import com.itsdf07.lib.mvp.presenter.IBaseMvpPresenter;
import com.itsdf07.lib.mvp.view.IBaseMvpView;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/21
 */
public interface MainContracts {
    interface IMainView extends IBaseMvpView<Activity> {
    }

    interface IMainPresenter extends IBaseMvpPresenter {
    }

    interface IMainModel extends IBaseMvpModel {
    }
}
