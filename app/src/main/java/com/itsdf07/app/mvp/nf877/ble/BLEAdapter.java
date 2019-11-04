package com.itsdf07.app.mvp.nf877.ble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itsdf07.app.mvp.R;
import com.itsdf07.lib.bt.ble.LinkedHashMap;
import com.itsdf07.lib.bt.ble.client.scan.BLEScanResult;


/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/4
 */
public class BLEAdapter extends BaseAdapter {
    private LinkedHashMap<String, BLEScanResult> scanedResults;
    //反射器
    LayoutInflater inflater;

    public BLEAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void updateBles(LinkedHashMap<String, BLEScanResult> scanedResults) {
        this.scanedResults = scanedResults;
    }

    @Override
    public int getCount() {
        return scanedResults.size();
    }

    @Override
    public Object getItem(int position) {
        return scanedResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_ble_item, null);
            convertView.setTag(viewholder);
            viewholder.tv_ble_name = convertView.findViewById(R.id.tv_ble_name);
            viewholder.tv_ble_mac = convertView.findViewById(R.id.tv_ble_mac);
            viewholder.tv_ble_rssi = convertView.findViewById(R.id.tv_ble_rssi);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.tv_ble_name.setText(scanedResults.get(position).getBluetoothDevice().getName());
        viewholder.tv_ble_mac.setText(scanedResults.get(position).getBluetoothDevice().getAddress());
        viewholder.tv_ble_rssi.setText(scanedResults.get(position).getRssi() + "");
        return convertView;
    }

    class ViewHolder {
        TextView tv_ble_name;
        TextView tv_ble_mac;
        TextView tv_ble_rssi;
    }
}
