package ru.share_paint.zoomparty.presentation

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.share_paint.zoomparty.domain.config.RemoteServiceProvider
import ru.share_paint.zoomparty.domain.BaseService
import ru.share_paint.zoomparty.domain.model.BluetoothWorkProfile
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.domain.config.Configuration.SHARED_PREF
import ru.share_paint.zoomparty.domain.createMessage
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val serviceProvider: RemoteServiceProvider
) : ViewModel() {
    private var remoteService: BaseService? = null
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

    fun connectionWithRemoteService(device: BluetoothDevice?) {
       remoteService = if(Configuration.profileDevice == BluetoothWorkProfile.MASTER ){
           Log.i(VIEW_MODEL_LOG,"serviceProvider.getMasterService()")
                serviceProvider.getMasterService()
            } else {
                if (device != null) {
                    Log.i(VIEW_MODEL_LOG,"serviceProvider.getSlaveService(device) | device $device")
                    serviceProvider.getSlaveService(device)
                } else{
                    Log.i(VIEW_MODEL_LOG,"ERROR | serviceProvider.getSlaveService(device) | device $device")
                    throw IOException("Error fun connectionWithRemoteService | bluetooth device  = null")
                }
            }
        remoteService!!.start()
        connectWithRemoteService(device)
    }

    private fun connectWithRemoteService(device: BluetoothDevice?) {
        viewModelScope.launch {
            try {
                remoteService?.openConnection(device)
            } catch (e: Throwable) {
                Log.e(Configuration.BT_LOG_TAG, "ERROR ServiceViewModel | fun connectWithRemoteService | remoteService?.openConnection(device):$e")
            }
        }
    }

    private fun sendData(byteArray: ByteArray) {
        viewModelScope.launch {
            remoteService?.sendData(byteArray)
        }
    }

    fun saveSettingToSharedPref(mode: BluetoothWorkProfile, device: BluetoothDevice?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_MODE_DEVICE, mode.mName)
        editor.putString(KEY_ADDRESS_DEVICE, device?.address ?: "")
        editor.apply()
    }

    fun readSettingToSharedPref() {
        val profileName = sharedPreferences.getString(KEY_MODE_DEVICE, BluetoothWorkProfile.MASTER.mName) ?: BluetoothWorkProfile.MASTER.mName
        Configuration.findProfileByName(profileName)
        val address = sharedPreferences.getString(KEY_ADDRESS_DEVICE, "00:00:00:00:00:00") ?: "00:00:00:00:00:00"
        Configuration.setLastDevice(address = address)
    }

    fun sendDragAmountToRemoteService(cX: Float, cY: Float, dX: Float, dY: Float, color:Long, widthLine:Float) {
        val dFrame = createMessage(cX, cY, dX, dY, color, widthLine)
        sendData(dFrame)
    }

    override fun onCleared() {
        super.onCleared()
        remoteService?.closeConnection()
        remoteService = null
    }

    companion object {
        const val KEY_MODE_DEVICE = "MODE_DEVICE"
        const val KEY_ADDRESS_DEVICE = "ADDRESS_DEVICE"
        const val VIEW_MODEL_LOG = "VIEW_MODEL_LOG"
    }
}
