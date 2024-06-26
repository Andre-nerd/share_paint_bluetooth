package ru.share_paint.zoomparty.presentation.sreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import ru.share_paint.zoomparty.presentation.ServiceViewModel
import ru.share_paint.zoomparty.presentation.navigation.Route
import ru.share_paint.zoomparty.presentation.sreens.setting.SelectGameOnceTwice
import ru.share_paint.zoomparty.presentation.ui.widgets.ProgressBarWidget
import ru.tic_tac_toe.zoomparty.R

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameScreen(serviceViewModel: ServiceViewModel, navController: NavHostController, dataContainer: WrapperDataContainer) {
    serviceViewModel.readSettingToSharedPref()
    val isShowSelectGameType = serviceViewModel.showSelectGameType.collectAsState()
    val showProgressBar = remember { mutableStateOf(false) }
    Box {
        DrawScreen(dataContainer) { cX, cY, dX, dY, color, width ->
            Log.e(Configuration.DRAW_LOG_TAG, "cX,cY,dX,dY -> $cX, $cY, $dX, $dY ->")
            serviceViewModel.sendDragAmountToRemoteService(cX, cY, dX, dY, color, width)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (showProgressBar.value) ProgressBarWidget()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isShowSelectGameType.value) {
            SelectGameOnceTwice(
                onceGame = {
                    serviceViewModel.setSelectGameType(false)

                },
                twiceGame = {
                    serviceViewModel.setSelectGameType(false)
                    navController.navigate(Route.Setting.name)
                }
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(id = R.drawable.ico_clean), contentDescription = null,
                modifier = Modifier.clickable {
                    dataContainer.cleanPathList()
                    dataContainer.cleanAllPointsFromMessage()
                })
            Spacer(modifier = Modifier.size(16.dp))
            Image(painter = painterResource(id = R.drawable.two_person), contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Route.Setting.name)
                })
            Spacer(modifier = Modifier.size(16.dp))
            Image(painter = painterResource(id = R.drawable.ico_help), contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Route.Help.name)
                })
        }
    }
}

@Composable
fun StartButton(callback: (Boolean) -> Unit) {
    var animated by remember { mutableStateOf(true) }
    val rotation = remember { androidx.compose.animation.core.Animatable(initialValue = 320f) }
    LaunchedEffect(animated) {
        rotation.animateTo(
            targetValue = if (animated) {
                0f
            } else {
                360f
            },
            animationSpec = tween(durationMillis = 1000),
        )
        delay(300)
        animated = !animated
    }
    Image(painter = painterResource(id = R.drawable.color_radius), contentDescription = "Start",
        modifier = Modifier
            .clickable {
                callback.invoke(false)
            }
            .graphicsLayer {
                rotationY = rotation.value
            }
    )
}