package ru.share_paint.zoomparty.domain.config

import android.bluetooth.BluetoothDevice
import ru.share_paint.zoomparty.data.service.bluetooth.master.MasterBluetoothService
import ru.share_paint.zoomparty.data.service.bluetooth.slave.SlaveBluetoothService
import ru.share_paint.zoomparty.domain.BaseService
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import javax.inject.Inject

class RemoteServiceProvider @Inject constructor(private val dataContainer: WrapperDataContainer) {
    fun getMasterService(): BaseService {
        return MasterBluetoothService(dataContainer)
    }
    fun getSlaveService(device: BluetoothDevice): BaseService {
        val service =  SlaveBluetoothService(dataContainer)
        service.setDevice(device)
        return service
    }
}