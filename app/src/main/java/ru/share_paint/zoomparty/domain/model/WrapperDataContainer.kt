package ru.share_paint.zoomparty.domain.model

import android.util.Log
import androidx.compose.ui.graphics.Path
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.share_paint.zoomparty.domain.config.Configuration.BT_LOG_TAG
import ru.share_paint.zoomparty.domain.config.Configuration.DRAW_LOG_TAG
import ru.share_paint.zoomparty.domain.config.Configuration.PATH_DATA
import ru.share_paint.zoomparty.domain.model.ParserMessages.getColorAndWidthLine
import ru.share_paint.zoomparty.domain.model.ParserMessages.getListPointsFromMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WrapperDataContainer @Inject constructor(){
    private val _pointsFromMessage = MutableStateFlow<List<DataPath>>(emptyList())
    val pointsFromMessage: StateFlow<List<DataPath>>
        get() = _pointsFromMessage
    private val _pathList = MutableStateFlow<List<DataPath>>(emptyList())
    val pathList: StateFlow<List<DataPath>>
        get() = _pathList

    fun putMessageLastReceivedToContainer(data: ByteArray) {
        Log.i(BT_LOG_TAG, "fun putMessageLastReceivedToContainer() | Get message ${data.toList()}")
        when(data[1]){
            PATH_DATA -> {
                val points  = getListPointsFromMessage(data)
                val sX = points[0] - points[2]
                val eX = points[0]
                val sY = points[1] - points[3]
                val eY = points[1]
                Log.i(DRAW_LOG_TAG, "points $sX | $sY | $eX | $eY")
                val path = Path()
                path.moveTo(sX,sY)
                path.lineTo(eX,eY)
                val attr = getColorAndWidthLine(data)
                val dataPath = DataPath(
                    path = path,
                    color = attr.first,
                    lWidth = attr.second
                )
                val list = _pointsFromMessage.value.toMutableList()
                list.add(dataPath)
                _pointsFromMessage.value = list
            }
        }
    }

    private val _errorConnect = MutableStateFlow<ErrorConnect>(ErrorConnect.NoError)
    val errorConnect: StateFlow<ErrorConnect>
        get() = _errorConnect

    fun putErrorConnectToContainer(errorConnect: ErrorConnect) {
        Log.i(BT_LOG_TAG, "fun putErrorConnectToContainer() | Get error ${errorConnect.name} ${errorConnect.message}")
        _errorConnect.value = errorConnect
    }
    fun cleanAllPointsFromMessage(){
        _pointsFromMessage.value = emptyList()
    }
    fun addToPathList(d: DataPath){
        val temp = _pathList.value.toMutableList()
        temp.add(d)
        _pathList.value = temp
    }
    fun cleanPathList(){
        _pathList.value = emptyList()
    }
}