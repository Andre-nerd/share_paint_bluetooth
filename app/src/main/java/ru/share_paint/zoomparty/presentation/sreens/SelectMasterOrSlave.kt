package ru.share_paint.zoomparty.presentation.sreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.share_paint.zoomparty.App
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.domain.model.BluetoothWorkProfile
import ru.share_paint.zoomparty.presentation.ServiceViewModel
import ru.share_paint.zoomparty.presentation.navigation.Route
import ru.share_paint.zoomparty.presentation.sreens.setting.BriefInstructionTwoDevice
import ru.share_paint.zoomparty.presentation.sreens.setting.FavoriteButton
import ru.share_paint.zoomparty.presentation.sreens.setting.SelectBTDevice
import ru.share_paint.zoomparty.presentation.ui.theme.GameStyleLarge
import ru.share_paint.zoomparty.presentation.ui.theme.largePadding
import ru.share_paint.zoomparty.presentation.ui.theme.nUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.widgets.ProgressBarWidget
import ru.tic_tac_toe.zoomparty.R

const val LOG_MASTER_SLAVE = "LOG_MASTER_SLAVE"

@Composable
fun SelectMasterOrSlave(serviceViewModel: ServiceViewModel, navController: NavHostController) {
    serviceViewModel.readSettingToSharedPref()
    Log.i(ServiceViewModel.VIEW_MODEL_LOG, "fun SelectMasterOrSlave Configuration.profileDevice | ${Configuration.profileDevice}")
    val workProfile = remember { mutableStateOf(Configuration.profileDevice) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val showProgressBar = remember { mutableStateOf(false) }
    val btNotConnected = stringResource(R.string.bluetooth_not_connected)
    if (App.bluetoothAdapter == null) {
        Toast.makeText(context, stringResource(R.string.bluetooth_not_available_twice_session_not_available), Toast.LENGTH_LONG).show()
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = sUiPadding.dp, end = sUiPadding.dp, top = nUiPadding.dp, bottom = nUiPadding.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MasterOrSlave { profile ->
                workProfile.value = profile
                Log.i(LOG_MASTER_SLAVE, workProfile.value.mName)
                Configuration.setProfileDevice(workProfile.value)
            }
            ShowRole(workProfile.value)
            Spacer(modifier = Modifier.size(largePadding.dp))
            if (workProfile.value == BluetoothWorkProfile.SLAVE) SelectBTDevice()
            Spacer(modifier = Modifier.size(largePadding.dp))
            BriefInstructionTwoDevice() {
                val device = Configuration.getSelectedDevice()
                Configuration.setProfileDevice(profile = workProfile.value)
                serviceViewModel.saveSettingToSharedPref(workProfile.value, device)
                try {
                    coroutineScope.launch(Dispatchers.IO) {
                        serviceViewModel.connectionWithRemoteService(Configuration.getSelectedDevice())
                        showProgressBar.value = true
                        delay(1000) // Задержка на установление связи между устройствами
                        showProgressBar.value = false
                        withContext(Dispatchers.Main) {
                            navController.navigate(Route.Game.name)
                        }
                    }

                } catch (t: Throwable) {
                    Toast.makeText(context, btNotConnected, Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (showProgressBar.value) ProgressBarWidget()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), contentAlignment = Alignment.TopEnd ){
        Image(painter = painterResource(id = R.drawable.ico_help), contentDescription = null,
            modifier = Modifier.clickable {
                navController.navigate(Route.Help.name)
            })
    }
}

@Composable
fun MasterOrSlave(callbackChoice: (BluetoothWorkProfile) -> Unit) {
    val isMaster = Configuration.profileDevice == BluetoothWorkProfile.MASTER
    val (isCheckedOne, setCheckedOne) = remember { mutableStateOf(isMaster) }
    val (isCheckedTwo, setCheckedTwo) = remember { mutableStateOf(!isMaster) }
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        FavoriteButton(
            isChecked = isCheckedOne,
            imageVector = Icons.Filled.AccountBox,
            onClick = {
                if (!isCheckedOne) {
                    setCheckedOne(!isCheckedOne)
                    setCheckedTwo(!isCheckedTwo)
                    val profile = BluetoothWorkProfile.MASTER
                    callbackChoice(profile)
                }
            }
        )
        FavoriteButton(
            isChecked = isCheckedTwo,
            imageVector = Icons.Filled.AccountCircle,
            onClick = {
                if (!isCheckedTwo) {
                    setCheckedTwo(!isCheckedTwo)
                    setCheckedOne(!isCheckedOne)
                    val profile = BluetoothWorkProfile.SLAVE
                    callbackChoice(profile)
                }
            }
        )
    }
}

@Composable
fun ShowRole(role: BluetoothWorkProfile) {
    val role = if(role == BluetoothWorkProfile.MASTER) stringResource(R.string.master_session) else stringResource(R.string.slave_session)
    Text(text = role, style = GameStyleLarge)
}
