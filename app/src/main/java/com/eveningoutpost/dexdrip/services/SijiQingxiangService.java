package com.eveningoutpost.dexdrip.services; // 注意包名

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import com.eveningoutpost.dexdrip.models.BgReading;
import com.eveningoutpost.dexdrip.services.G5BaseService; // 注意继承的基类

public class SijiQingxiangService extends G5BaseService { // 继承G5BaseService
    private static final String TAG = "SijiQingxiang";
    
    // 使用你提供的UUID
    private static final UUID SERVICE_UUID = UUID.fromString("0000ff30-0000-1000-8000-00805f9b34fb");
    private static final UUID NOTIFY_UUID = UUID.fromString("0000ff31-0000-1000-8000-00805f9b34fb");
    private static final UUID WRITE_UUID = UUID.fromString("0000ff32-0000-1000-8000-00805f9b34fb");
    
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        if (NOTIFY_UUID.equals(characteristic.getUuid())) {
            byte[] data = characteristic.getValue();
            if (data == null || data.length < 4) return;
            
            // 使用你提供的解析算法
            int raw = (data[3] & 0xFF) << 8 | (data[2] & 0xFF);
            double mmol = raw / 7200.0;
            BgReading.create((int)(mmol * 18), mmol, System.currentTimeMillis(), "SijiQingxiang");
        }
    }
    
    // 需要重写基类方法，返回对应的UUID
    @Override
    protected String getServiceUUID() { return SERVICE_UUID.toString(); }
    @Override
    protected String getNotifyUUID() { return NOTIFY_UUID.toString(); }
    @Override
    protected String getWriteUUID() { return WRITE_UUID.toString(); }
    @Override
    protected String getTag() { return TAG; }
}
