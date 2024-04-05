package ru.share_paint.zoomparty.domain.config

import android.bluetooth.BluetoothDevice
import ru.share_paint.zoomparty.domain.model.BluetoothWorkProfile

object Configuration {
    const val BT_LOG_TAG = "BT_LOG_TAG"
    const val DRAW_LOG_TAG = "DRAW_LOG_TAG"
    const val F_BUFFER = 1
    const val DATA_BUFFER = 33 // Общая длина массива данных с заголовочным байтом  = 34
    const val F_BUFFER_VALUE = 36.toByte()
    const val PATH_DATA = 1.toByte()
    const val SHARED_PREF = "SHARED_PREF"

    // Mode device MASTER or SLAVE
    var profileDevice: BluetoothWorkProfile = BluetoothWorkProfile.MASTER
        private set
    fun setProfileDevice(profile: BluetoothWorkProfile) {
        profileDevice = profile
    }
    fun findProfileByName(name:String){
        val profile  = if(name == BluetoothWorkProfile.SLAVE.mName) BluetoothWorkProfile.SLAVE else BluetoothWorkProfile.MASTER
        setProfileDevice(profile = profile)
    }

    // Last BT device
    private var lDeviceMacAddress:String? = null
        private set
    fun setLastDevice(device: BluetoothDevice) {
        lDeviceMacAddress = device.address
    }
    fun setLastDevice(address:String) {
        lDeviceMacAddress = address
    }

    //List bounded devices
    var boundedDevices: MutableList<BluetoothDevice> = mutableListOf()
        private  set
    fun setBoundedDevices(set:Set<BluetoothDevice>){
        boundedDevices.addAll(set)
    }
    fun getIndexSelectedDevice():Int{
        boundedDevices.forEachIndexed { index, value ->
            if (value.address == lDeviceMacAddress) return index
        }
        return -1
    }
    fun getSelectedDevice():BluetoothDevice? {
        return boundedDevices.find { it.address == lDeviceMacAddress }
    }
}
