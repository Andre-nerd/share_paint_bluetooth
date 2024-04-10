package ru.share_paint.zoomparty.presentation.help


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.share_paint.zoomparty.presentation.help.cahpter.HelpAboutApp
import ru.share_paint.zoomparty.presentation.help.cahpter.HelpModeOfGame
import ru.share_paint.zoomparty.presentation.help.cahpter.HelpPrivacyPolicy
import ru.share_paint.zoomparty.presentation.help.cahpter.HelpTwiceSession
import ru.share_paint.zoomparty.presentation.navigation.Route
import ru.share_paint.zoomparty.presentation.ui.theme.BottomBarPadding
import ru.share_paint.zoomparty.presentation.ui.theme.nUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.tic_tac_toe.zoomparty.R


@Composable
fun HelpScreen(navController: NavHostController) {
    var openArticleAboutApp by remember { mutableStateOf(false) }
    var openArticleTwiceSession by remember { mutableStateOf(false) }
    var openArticleModeOfGame by remember { mutableStateOf(false) }
    var openArticlePrivacyPolicy by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = sUiPadding.dp,
                    end = sUiPadding.dp,
                    top = nUiPadding.dp,
                    bottom = (BottomBarPadding).dp
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = { openArticleAboutApp = !openArticleAboutApp }
            ) {
                Text(text = stringResource(R.string.about_app), textAlign = TextAlign.Center)
            }
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = { openArticleModeOfGame  = !openArticleModeOfGame  }
            ) {
                Text(text = stringResource(R.string.mode_of_game), textAlign = TextAlign.Center)
            }
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = { openArticleTwiceSession = !openArticleTwiceSession }
            ) {
                Text(text = stringResource(id = R.string.twice_session_title), textAlign = TextAlign.Center)
            }
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = { openArticlePrivacyPolicy = !openArticlePrivacyPolicy }
            ) {
                Text(text = stringResource(id = R.string.privacy_policy_title), textAlign = TextAlign.Center)
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth()){
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    navController.navigate(Route.Setting.name)
                }
                ) {
                    Text(text = stringResource(R.string.back_to_setting))
                }
            }
            Spacer(modifier = Modifier.size(32.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    navController.navigate(Route.Game.name)
                }
                ) {
                    Text(text = stringResource(R.string.back_to_game))
                }
            }
        }

    }
    if (openArticleAboutApp) HelpAboutApp { openArticleAboutApp = !openArticleAboutApp }
    if (openArticleTwiceSession) HelpTwiceSession {openArticleTwiceSession = !openArticleTwiceSession}
    if (openArticleModeOfGame) HelpModeOfGame{openArticleModeOfGame = !openArticleModeOfGame}
    if (openArticlePrivacyPolicy) HelpPrivacyPolicy {openArticlePrivacyPolicy = !openArticlePrivacyPolicy}

}

