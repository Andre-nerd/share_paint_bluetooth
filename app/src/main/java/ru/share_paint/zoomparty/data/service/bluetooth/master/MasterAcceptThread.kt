package ru.share_paint.zoomparty.data.service.bluetooth.master

import android.annotation.SuppressLint
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import ru.share_paint.zoomparty.App
import ru.share_paint.zoomparty.domain.config.Configuration.BT_LOG_TAG
import ru.share_paint.zoomparty.domain.reportAboutCrash
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class MasterAcceptThread(private val callback:(s: BluetoothSocket)->Unit) : Thread() {
    private val uuid: UUID = UUID.fromString("e17adf11-0edf-4263-ba1d-f84bd12e5be2")
    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        App.bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord("slave", uuid )
    }

    override fun run() {
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                mmServerSocket?.accept()
            } catch (e: IOException) {
                Log.e(BT_LOG_TAG, "Socket's accept() method failed", e)
                reportAboutCrash("class MasterAcceptThread | Socket's accept() method failed", e)
                shouldLoop = false
                null
            }
            socket?.also {
                Log.e(BT_LOG_TAG, "MasterAcceptThread | Socket's created $socket ${socket.remoteDevice.address}")
                callback.invoke(socket)
                mmServerSocket?.close()
                shouldLoop = false
            }
        }
    }

    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
            Log.e(BT_LOG_TAG, "Could not close the connect socket", e)
            reportAboutCrash("class MasterAcceptThread | Could not close the connect socket", e)
        }
    }
}