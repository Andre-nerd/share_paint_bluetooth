package ru.share_paint.zoomparty.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class DataPath(
    val path:Path = Path(),
    val color: ULong = Color.DarkGray.value,
    val lWidth: Float = 10F
)