package ru.share_paint.zoomparty.presentation.help.cahpter


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.share_paint.zoomparty.presentation.ui.theme.fontSize
import ru.share_paint.zoomparty.presentation.ui.theme.nPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.tic_tac_toe.zoomparty.R


@Composable
fun HelpModeOfGame(
    onDismissRequest:() -> Unit,
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(sUiPadding.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.mode_of_game),
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start),
                    text = stringResource(R.string.help_mode_of_game_1),
                    style = styleAboutText
                )
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(nPadding.dp)
                        .clickable {},
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.help_mode__of_game_1_1), contentDescription = null,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(top = 32.dp),
                    ) {
                        Text(stringResource(id = R.string.close_dialog))
                    }
                }
            }
        }
    }
}