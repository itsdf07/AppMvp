package com.itsdf07.app.mvp.nf877.ble;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.itsdf07.app.mvp.R;
import com.itsdf07.lib.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public class BLEScanActivity extends BaseMvpActivity<BLEScanPresenter> implements BLEScanContracts.IBLEScanView {
    @BindView(R.id.btn_start_scan_ble)
    Button btnStartScanBle;
    @BindView(R.id.btn_stop_scan_ble)
    Button btnStopScanBle;
    @BindView(R.id.lv_bles)
    ListView lvBles;

    public BLEAdapter bleAdapter;

    @Override
    public BLEScanPresenter onInitPresenter() {
        return new BLEScanPresenter(this);
    }

    @Override
    public void onAfterPresenter() {
        bleAdapter.updateBles(presenter.getBLEs());
        lvBles.setAdapter(bleAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ble_scan;
    }

    @Override
    public void onBeforeView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onAfterView() {
        bleAdapter = new BLEAdapter(this);
    }

    @Override
    public BLEScanActivity getSelfActivity() {
        return this;
    }

    @OnClick({R.id.btn_start_scan_ble, R.id.btn_stop_scan_ble})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_scan_ble:
                presenter.startScan();
                break;
            case R.id.btn_stop_scan_ble:
                presenter.stopScan();
                break;
        }
    }

    @Override
    public void notifyUpdata2Adapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void notifyUpdata2Item(final int index) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updatePosition(index);
            }
        });
    }

    public void updatePosition(int posi) {
        int visibleFirstPosi = lvBles.getFirstVisiblePosition();
        int visibleLastPosi = lvBles.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = lvBles.getChildAt(posi - visibleFirstPosi);
            BLEAdapter.ViewHolder holder = (BLEAdapter.ViewHolder) view.getTag();
        }
    }
}
