package ru.share_paint.zoomparty.presentation.help.cahpter


import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.share_paint.zoomparty.presentation.ui.theme.fontSize
import ru.share_paint.zoomparty.presentation.ui.theme.nPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.share_paint.zoomparty.presentation.ui.theme.stylePost
import ru.tic_tac_toe.zoomparty.R


@Composable
fun HelpAboutApp(
    onDismissRequest: () -> Unit,
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(sUiPadding.dp),
                    text = stringResource(R.string.info_about_app),
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(sUiPadding.dp),
                    text = stringResource(R.string.draw_together_1_1),
                    style = styleAboutText
                )

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(nPadding.dp)
                        .clickable { },
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.help_draw_1_3),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(sUiPadding.dp),
                    text = stringResource(R.string.draw_together_1_2),
                    style = styleAboutText
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(sUiPadding.dp),
                    text = stringResource(R.string.draw_together_1_3),
                    style = styleAboutText
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(sUiPadding.dp),
                    text = stringResource(R.string.version_app),
                    style = styleAboutText
                )
                ClickableText(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = sUiPadding.dp, bottom = sUiPadding.dp),
                    style = stylePost ,
                    text = AnnotatedString("nrdevelop@yandex.ru"),
                    onClick = { offset ->
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.setType("text/plain")
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share paint comment")
                        intent.setData(Uri.parse("mailto:nrdevelop@yandex.ru"))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
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