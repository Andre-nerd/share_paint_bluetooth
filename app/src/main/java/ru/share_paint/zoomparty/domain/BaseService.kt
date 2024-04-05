package ru.share_paint.zoomparty.domain

import android.bluetooth.BluetoothDevice

interface BaseService {
    suspend fun openConnection(device:BluetoothDevice? = null)
    fun closeConnection()
    suspend fun sendData(data: ByteArray)
    suspend fun receiveData()
    fun start()
}