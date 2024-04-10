package ru.share_paint.zoomparty.presentation.help.cahpter


import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.share_paint.zoomparty.presentation.ui.theme.fontSize
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.share_paint.zoomparty.presentation.ui.theme.stylePost
import ru.tic_tac_toe.zoomparty.R


@Composable
fun HelpPrivacyPolicy(
    onDismissRequest:() -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val context = LocalContext.current
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
                    text = stringResource(R.string.privacy_policy_title),
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(sUiPadding.dp),
                    text = "Приложение не собирает какие-либо личные данные о пользователе, которые позволяют его идентифицировать. Боллее подробней о политике конфиденциальности вы можете ознакомиться по ссылке: ",
                    style = styleAboutText
                )
                ClickableText(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = sUiPadding.dp, bottom = sUiPadding.dp),
                    style = stylePost ,
                    text = AnnotatedString("Privacy policy for mobile application \"Share Paint\""),
                    onClick = { offset ->
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/nrdevelopdoc/share_paint/blob/main/README.md")
                        )
                        context.startActivity(urlIntent)
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