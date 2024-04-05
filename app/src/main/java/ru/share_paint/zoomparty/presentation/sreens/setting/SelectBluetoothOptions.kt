package ru.share_paint.zoomparty.presentation.sreens.setting

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.presentation.sreens.getHeightDialogWindow
import ru.share_paint.zoomparty.presentation.ui.theme.CianLight
import ru.share_paint.zoomparty.presentation.ui.theme.KashmirFamily
import ru.share_paint.zoomparty.presentation.ui.theme.Orange
import ru.share_paint.zoomparty.presentation.ui.theme.fontSizeSmall
import ru.share_paint.zoomparty.presentation.ui.theme.nPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.tic_tac_toe.zoomparty.R


@SuppressLint("MissingPermission")
@Composable
fun SelectBTDevice() {
    var nameDevice: String = stringResource(R.string.no_name)
    var macDevice = stringResource(R.string._00_00_00_00_00_00)
    val lastDevice = Configuration.getSelectedDevice()
    if (lastDevice != null) {
        nameDevice = lastDevice.name
        macDevice = lastDevice.address
    }
    val fullName = "$nameDevice : $macDevice"

    var openSelectBtDevice by remember { mutableStateOf(false) }
    var selectedDevice by remember { mutableStateOf(nameDevice) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Подключиться к мастер-устройству:", fontFamily = KashmirFamily, fontSize = 24.sp, color = CianLight)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = fullName, style = styleAboutText, color = Orange)
        IconButton(onClick = { openSelectBtDevice = !openSelectBtDevice }) {
            Icon(Icons.Filled.List, contentDescription = "Select", tint = Orange)
        }
    }
    if (openSelectBtDevice) {
        var nSelected = Configuration.getIndexSelectedDevice()
        if (nSelected == -1) nSelected = 0
        DialogSelectBTDevice(
            title = "Cписок мастер-устройств",
            subTitle = null,
            radioOptions = Configuration.boundedDevices,
            nSelectedElement = nSelected,
            onDismissRequest = { openSelectBtDevice = !openSelectBtDevice },
            onConfirmation = { device ->
                selectedDevice = device.name
                openSelectBtDevice = !openSelectBtDevice
                Configuration.setLastDevice(device)
            }
        )
    }
}

@SuppressLint("MissingPermission")
@Composable // Принимает на вход список DeviceItem
fun DialogSelectBTDevice(
    title: String?,
    subTitle: String?,
    radioOptions: List<BluetoothDevice>,
    nSelectedElement: Int,
    onDismissRequest: () -> Unit,
    onConfirmation: (BluetoothDevice) -> Unit,
) {
    if (radioOptions.isEmpty()) {
        Toast.makeText(LocalContext.current, "Доверенных устройств не найдено. Проверьте подключение bluetooth", Toast.LENGTH_LONG).show()
        return
    }
    val selected = if (nSelectedElement > radioOptions.size - 1) 0 else nSelectedElement
    var selectedOption by remember { mutableStateOf(radioOptions[selected]) }

    val cHeight = getHeightDialogWindow(radioOptions.size)

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(cHeight.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(nPadding.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                if (title != null) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(sUiPadding.dp),
                        text = title,
                        fontSize = fontSizeSmall.sp
                    )
                }
                if (subTitle != null) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = subTitle,
                        fontSize = fontSizeSmall.sp
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                radioOptions.forEach { choiceName ->
                    val nameDevice = choiceName.name ?: stringResource(R.string.no_name)
                    val macDevice = choiceName.address ?: stringResource(R.string._00_00_00_00_00_00)
                    val fullName = "$nameDevice : $macDevice"
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (choiceName == selectedOption),
                            onClick = { selectedOption = choiceName }
                        )
                        Text(
                            text = fullName,
                            fontSize = fontSizeSmall.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.dismiss_dialog))
                    }
                    TextButton(
                        onClick = { onConfirmation(selectedOption) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.confirm_dialog))
                    }
                }
            }

        }
    }
}