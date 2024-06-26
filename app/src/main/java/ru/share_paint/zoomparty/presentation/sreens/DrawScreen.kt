package ru.share_paint.zoomparty.presentation.sreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ru.share_paint.zoomparty.domain.model.DataPath
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import ru.share_paint.zoomparty.presentation.ui.theme.Brown
import ru.share_paint.zoomparty.presentation.ui.theme.Orange
import ru.share_paint.zoomparty.presentation.ui.theme.SkyBlue
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding

@Composable
fun DrawScreen(dataContainer: WrapperDataContainer, callback: (cX: Float, cY: Float, dX: Float, dY: Float, color:Long, widthLine:Float) -> Unit) {
    var tempPath = Path()
    val pathList = dataContainer.pathList.collectAsState()
    var lineWidth by remember { mutableFloatStateOf(10f) }
    val exPathList = dataContainer.pointsFromMessage.collectAsState()


    Column {
        val dataPath = remember { mutableStateOf(DataPath())}
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.80f)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { tempPath = Path() }
                ) { change, dragAmount ->
                    tempPath.moveTo(change.position.x - dragAmount.x, change.position.y - dragAmount.y)
                    tempPath.lineTo(change.position.x, change.position.y)
                    callback.invoke(
                        change.position.x,
                        change.position.y,
                        dragAmount.x,
                        dragAmount.y,
                        dataPath.value.color.toLong(),
                        dataPath.value.lWidth
                    )
                    dataContainer.addToPathList(dataPath.value.copy(path = tempPath))
                }
            }) {
            pathList.value.forEach { dataPath ->
                drawPath(
                    path = dataPath.path,
                    color = Color(dataPath.color),
                    style = Stroke(width = dataPath.lWidth, cap = StrokeCap.Round)
                )
            }
            exPathList.value.forEach {exDataPath->
                drawPath(
                    path = exDataPath.path,
                    color = Color(exDataPath.color),
                    style = Stroke(width = exDataPath.lWidth, cap = StrokeCap.Round)
                )
            }

        }
        ColorPalette(callback = { color -> dataPath.value = dataPath.value.copy( color = color.value) })
        Slider(
            value = lineWidth,
            onValueChange = {width->
                lineWidth = width
                dataPath.value  = dataPath.value.copy( lWidth = width)
            },
            valueRange = 1f..50f,
            steps = 49
        )
    }
}

@Composable
fun ColorPalette(callback: (color:Color) -> Unit) {
    val colors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Blue,
        Color.Cyan,
        Color.Magenta,
        Color.Green,
        Color.DarkGray,
        SkyBlue,
        Brown,
        Orange

    )
    Column {
        LazyRow(modifier = Modifier.padding(sUiPadding.dp)) {
            items(colors) { color ->
                Box(modifier = Modifier
                    .padding(sUiPadding.dp)
                    .clickable { callback.invoke(color) }
                    .size((sUiPadding * 2).dp)
                    .background(color, CircleShape)
                )
            }
        }
    }
}