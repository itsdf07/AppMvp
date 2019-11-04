package com.itsdf07.lib.bt.ble.server.core;


import com.itsdf07.lib.bt.ble.common.OKBLECharacteristicModel;
import com.itsdf07.lib.bt.ble.common.OKBLEServiceModel;

import java.util.List;

/**
 * Created by a1anwang.com on 2018/5/30.
 */

public interface OKBLEServerDevice {


    void addCharacteristic(List<OKBLECharacteristicModel> okbleCharacteristicModels, OKBLEServiceModel okbleServiceModel, OKBLEServerOperation.BLEServerOperationListener operationListener);

    void reSet();
}
