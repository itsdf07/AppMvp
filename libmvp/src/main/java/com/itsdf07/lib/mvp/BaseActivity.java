package com.itsdf07.lib.mvp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/10/21
 */
public abstract class BaseActivity extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getLayoutId() > 0) {
            setContentView(this.getLayoutId());
        } else {
            Log.w(TAG, "请在getLayoutId中设置您的UI布局");
        }
        onBeforeView();
        onInitView();
        onAfterView();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 用来处理UI控件初始化之前要做的事情
     */
    public abstract void onBeforeView();


    /**
     * 初始化UI控件
     */
    public abstract void onInitView();


    /**
     * 初始化之后UI控件要做的事情
     */
    public abstract void onAfterView();

}
