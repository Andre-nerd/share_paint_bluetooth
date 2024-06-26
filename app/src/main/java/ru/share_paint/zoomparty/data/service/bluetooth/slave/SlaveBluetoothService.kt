package ru.share_paint.zoomparty.data.service.bluetooth.slave

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.share_paint.zoomparty.App
import ru.share_paint.zoomparty.domain.BaseService
import ru.share_paint.zoomparty.domain.config.Configuration.BT_LOG_TAG
import ru.share_paint.zoomparty.domain.config.Configuration.DATA_BUFFER
import ru.share_paint.zoomparty.domain.config.Configuration.F_BUFFER
import ru.share_paint.zoomparty.domain.config.Configuration.F_BUFFER_VALUE
import ru.share_paint.zoomparty.domain.model.ErrorConnect
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import java.io.IOException
import javax.inject.Inject


@SuppressLint("MissingPermission")
class SlaveBluetoothService @Inject constructor(private val dataContainer: WrapperDataContainer) : Thread(), BaseService {
    private var slaveBindThread: SlaveBindThread? = null
    private var device: BluetoothDevice? = null
    private var mmSocket: BluetoothSocket? = null

    fun setDevice(device: BluetoothDevice) {
        this.device = device
    }

    override fun run() {
        App.bluetoothAdapter?.cancelDiscovery()
    }

    override suspend fun receiveData() {
        var numBytes: Int
        val fBuffer = ByteArray(F_BUFFER)
        val dataBuffer = ByteArray(DATA_BUFFER)
        while (true) {
            Log.d(BT_LOG_TAG, "SlaveBluetoothService | fun receiveData() running... ")
            numBytes = try {
                mmSocket?.inputStream?.read(fBuffer)
                if (fBuffer[0] != F_BUFFER_VALUE) {
                    Log.e(BT_LOG_TAG, "SlaveBluetoothService | Get first byte != $F_BUFFER_VALUE ${fBuffer[0]} | Continue receive ")
                    continue
                }
                Log.d(BT_LOG_TAG, "NEXT F_BUFFER_VALUE ")
                mmSocket?.inputStream?.read(dataBuffer)
                dataContainer.putMessageLastReceivedToContainer(fBuffer + dataBuffer)
                fBuffer.size + dataBuffer.size
            } catch (e: IOException) {
                Log.d(BT_LOG_TAG, "Input stream was disconnected", e)
                dataContainer.putErrorConnectToContainer(ErrorConnect.DisconnectSlaveError(e.message.toString()))
                break
            }
        }
    }

    override suspend fun openConnection(device: BluetoothDevice?) {
        if (this.device == null) {
            Log.d(BT_LOG_TAG, "SlaveBindService | device  == null | open connection failed")
            return
        }
        slaveBindThread = SlaveBindThread(this.device!!) { socket ->
            mmSocket = socket
            Log.i(BT_LOG_TAG, "Client | SocketServiceBT | socket = $mmSocket")

            if (mmSocket == null) {
                Log.i(BT_LOG_TAG, "Client | SocketServiceBT | null socket return false")
            }
            if (mmSocket?.isConnected == true) {
                Log.i(BT_LOG_TAG, "Client | SocketServiceBT | mmSocket?.isConnected ${mmSocket?.isConnected}")
            }
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scope.launch {
                if (mmSocket?.isConnected == true) {
                    Log.d(BT_LOG_TAG, "ConnectedThread fun while (true){")
                        receiveData()
                }
            }
        }
        slaveBindThread?.start()
    }


    override suspend fun sendData(data: ByteArray) {
        try {
            Log.e(BT_LOG_TAG, "Server send : ${data.toList()}")
            withContext(Dispatchers.IO) {
                mmSocket?.outputStream?.write(data)
            }
        } catch (e: IOException) {
            Log.e(BT_LOG_TAG, "Error occurred when sending data", e)
            return
        }
    }

    override fun closeConnection() {
        try {
            mmSocket?.close()
            slaveBindThread?.cancel()
            Log.e(BT_LOG_TAG, "Client | ConnectBluetoothThread | mmSocket?.close() success $mmSocket")
        } catch (e: IOException) {
            Log.e(BT_LOG_TAG, "Client | Could not close the client socket", e)
        }
    }
}
