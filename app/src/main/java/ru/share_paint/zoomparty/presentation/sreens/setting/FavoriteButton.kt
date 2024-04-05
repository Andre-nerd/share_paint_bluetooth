package ru.share_paint.zoomparty.presentation.sreens.setting

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.share_paint.zoomparty.presentation.ui.theme.cMagenta
import ru.share_paint.zoomparty.presentation.ui.theme.minMagenta

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FavoriteButton(
    isChecked: Boolean,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    IconToggleButton(
        modifier = Modifier.size(120.dp),
        checked = isChecked,
        onCheckedChange = { onClick() }
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint by transition.animateColor(
            label = "Tint"
        ) { isChecked ->
            if (isChecked) cMagenta else minMagenta
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        90.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        100.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        110.dp at 75 // ms
                        120.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 90.dp }

        Icon(
//            imageVector = if (isChecked) Icons.Filled.AccountBox else Icons.Filled.AccountCircle,
            imageVector = imageVector,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}

@Preview("Favorite Button")
@Composable
fun FavoriteButtonPreview() {
    val (isCheckedOne, setCheckedOne) = remember { mutableStateOf(true) }
    val (isCheckedTwo, setCheckedTwo) = remember { mutableStateOf(false) }
    MaterialTheme {
        Surface {
            Column {
                FavoriteButton(
                    isChecked = isCheckedOne,
                    imageVector = Icons.Filled.AccountBox,
                    onClick = { setCheckedOne(!isCheckedOne) }
                )
                FavoriteButton(
                    isChecked = isCheckedTwo,
                    imageVector = Icons.Filled.AccountCircle,
                    onClick = { setCheckedTwo(!isCheckedTwo) }
                )
            }

        }
    }
}