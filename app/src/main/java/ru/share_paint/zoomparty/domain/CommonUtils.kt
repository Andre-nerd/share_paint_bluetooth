package ru.share_paint.zoomparty.domain

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.share_paint.zoomparty.domain.config.Configuration
import ru.share_paint.zoomparty.domain.config.Configuration.DRAW_LOG_TAG
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun getFloatFromByteArray(array: ByteArray): Float{
    return try {
        ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).float
    } catch (t: Throwable) {
        Log.e(DRAW_LOG_TAG, "Error getFloatFromByteArray()")
        0f
    }
}
fun getULongFromByteArray(array: ByteArray): ULong{
    return try {
        ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).long.toULong()
    } catch (t: Throwable) {
        Log.e(DRAW_LOG_TAG, "Error getFloatFromByteArray()")
        0uL
    }
}

fun Float.getByteArrayFromFloat():ByteArray {
    return ByteBuffer.allocate(4).putFloat(this).array()
}
fun Long.getByteArrayFromLong():ByteArray {
    return ByteBuffer.allocate(8).putLong(this).array()
}
fun Int.getByteArrayFromInt():ByteArray {
    return ByteBuffer.allocate(4).putInt(this).array()
}

/**
 *    0      /       1       / 2         /  6               /     10              /  14    /  18       /     22   /  30       /
 * заголовок/   спецификатор /id игрока / начальная точка X / начальная точка Y / offset X  / offset Y / цвет линии   / ширина линии /
 *  byte    /       byte     / int      / float             / float             / float     / float    /     long     / float        /
 *  1 byte  /       1 byte   / 4 byte   /  4 byte           /     4 byte        / 4 byte    / 4 byte   /     8 byte   / 4 byte       /
 */
fun createMessage(cX: Float, cY: Float, dX: Float, dY: Float, color:Long, widthLine:Float):ByteArray{
    val dFrame = ByteArray(34)
    dFrame[0] = 36.toByte() // Заголовочный байт
    dFrame[1] = 1.toByte()  // Спецификатор
    for (i in 2..5) dFrame[i] = 0 // id Игрока
    for (i in 6..9) dFrame[i] = cX.getByteArrayFromFloat()[i - 6]
    for (i in 10..13) dFrame[i] = cY.getByteArrayFromFloat()[i - 10]
    for (i in 14..17) dFrame[i] = dX.getByteArrayFromFloat()[i - 14]
    for (i in 18..21) dFrame[i] = dY.getByteArrayFromFloat()[i - 18]
    for (i in 22..29) dFrame[i] = color.getByteArrayFromLong()[i - 22]
    for (i in 30..33) dFrame[i] = widthLine.getByteArrayFromFloat()[i - 30]
    Log.e(Configuration.DRAW_LOG_TAG, "sendDragAmountToRemoteService | ${dFrame.toList()}")
    val tempFloat = byteArrayOf(dFrame[6], dFrame[7], dFrame[8], dFrame[9])
    Log.e(Configuration.DRAW_LOG_TAG, "float | ${getFloatFromByteArray(tempFloat)}")
    val tempLong = byteArrayOf(dFrame[22], dFrame[23], dFrame[24], dFrame[25],dFrame[26], dFrame[27], dFrame[28], dFrame[29])
    Log.e(Configuration.DRAW_LOG_TAG, "long | ${getULongFromByteArray(tempLong)}")
    Log.e(Configuration.DRAW_LOG_TAG, "DarkGrey | ${Color.DarkGray.value}")
    return dFrame
}

fun EditText.changeTextFlow(): Flow<String> {
    return callbackFlow {
        val textChangeListener = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        this@changeTextFlow.addTextChangedListener(textChangeListener)

        awaitClose{
            this@changeTextFlow.removeTextChangedListener(textChangeListener)
        }
    }
}