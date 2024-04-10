package ru.share_paint.zoomparty.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint
import ru.share_paint.zoomparty.App
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import ru.share_paint.zoomparty.presentation.navigation.NavigateRoute
import ru.share_paint.zoomparty.presentation.ui.theme.Share_Paint_Theme
import ru.share_paint.zoomparty.presentation.ui.widgets.ShowScreenRationalePermission
import ru.tic_tac_toe.zoomparty.R
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    private val pushOnBluetoothLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
        }
    }

    @Inject
    lateinit var dataContainer: WrapperDataContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (App.bluetoothAdapter == null) {
            Toast.makeText(this, getString(R.string.bluetooth_not_available_twice_session_not_available), Toast.LENGTH_LONG).show()
        }
        if (App.bluetoothAdapter != null && !App.bluetoothAdapter!!.isEnabled) {
            openBTActivityForResult()
        }
        setContent {
            Share_Paint_Theme {
                FeatureThatRequiresPermissions()
            }
        }
    }


    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun FeatureThatRequiresPermissions() {

        val permissionsState = rememberMultiplePermissionsState(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("PERMISSION_GET", "Build.VERSION.SDK_INT >= Build.VERSION_CODES.S")
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                )
            } else {
                Log.d("PERMISSION_GET", "Build.VERSION.SDK_INT < Build.VERSION_CODES.S")
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH_ADMIN
                )
            }
        )
        if (permissionsState.allPermissionsGranted) {
            Log.d(Configuration.BT_LOG_TAG, "bluetoothAdapter!!.bondedDevices ${App.bluetoothAdapter!!.bondedDevices.size} ")
            Configuration.setBoundedDevices(App.bluetoothAdapter!!.bondedDevices)
            Box(modifier = Modifier.fillMaxWidth()) {
                MainScreen(dataContainer)
            }
        } else {
            ShowScreenRationalePermission(permissionsState)
        }
    }

    private fun openBTActivityForResult() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        pushOnBluetoothLauncher.launch(intent)
    }
}

@Composable
fun MainScreen(dataContainer: WrapperDataContainer) {
    val navController = rememberNavController()
    NavigateRoute(navController = navController, dataContainer = dataContainer)
}



