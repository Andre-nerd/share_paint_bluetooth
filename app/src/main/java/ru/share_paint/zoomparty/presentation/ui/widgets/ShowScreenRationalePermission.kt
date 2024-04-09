package ru.share_paint.zoomparty.presentation.ui.widgets
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import ru.tic_tac_toe.zoomparty.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowScreenRationalePermission(permissionsState:MultiplePermissionsState){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(36.dp)) {
            val textToShow = if (permissionsState.shouldShowRationale) {
                stringResource(R.string.bluetooth_for_cange_data_between_devices)
            } else {
                stringResource(R.string.bluetooth_for_cange_data_between_devices)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(textToShow)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(stringResource(R.string.for_correct_work_need_permission))
            }
            Spacer(modifier = Modifier.size(40.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = {
            permissionsState.launchMultiplePermissionRequest()
                }) {
                    Text(stringResource(R.string.push_permission))
                }
            }
        }
    }
}