package ru.share_paint.zoomparty.presentation.help


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.share_paint.zoomparty.presentation.help.cahpter.HelpAboutApp
import ru.share_paint.zoomparty.presentation.navigation.Route
import ru.share_paint.zoomparty.presentation.sreens.StartButton
import ru.share_paint.zoomparty.presentation.ui.theme.BottomBarPadding
import ru.share_paint.zoomparty.presentation.ui.theme.nUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding



@Composable
fun HelpScreen(navController: NavHostController) {
    var openArticleAboutApp by remember { mutableStateOf(false) }
    var openArticleLaunchApp by remember { mutableStateOf(false) }
    var openArticleNavAndTelemetry by remember { mutableStateOf(false) }
    var openArticleAmendment by remember { mutableStateOf(false) }
    var openArticleMap by remember { mutableStateOf(false) }
    var openArticleSetting by remember { mutableStateOf(false) }
    var openArticleEGTSInterval by remember { mutableStateOf(false) }
    var openArticleDownload by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = sUiPadding.dp, end = sUiPadding.dp, top = nUiPadding.dp, bottom = (BottomBarPadding).dp)
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
            Text(text = "Информация о приложении", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleLaunchApp = !openArticleLaunchApp }
        ) {
            Text(text = "Запуск приложения", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleNavAndTelemetry = !openArticleNavAndTelemetry }
        ) {
            Text(text = "Навигационное решение и телеметрия", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleAmendment = !openArticleAmendment }
        ) {
            Text(text = "Подписка на получение поправок RTK | PPP", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleMap = !openArticleMap }
        ) {
            Text(text = "Экран \"Карта\"", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleSetting = !openArticleSetting }
        ) {
            Text(text = "Экран \"Настройки\"", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleEGTSInterval = !openArticleEGTSInterval }
        ) {
            Text(text = "Настройка интервала отправки сообщений на ЕГТС Сервер", textAlign = TextAlign.Center)
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { openArticleDownload = !openArticleDownload }
        ) {
            Text(text = "Как скачать лог-файлы с Терминала на телефон", textAlign = TextAlign.Center)
        }
        StartButton(){
            navController.navigate(Route.Game.name)
        }
    }
    if (openArticleAboutApp) HelpAboutApp { openArticleAboutApp = !openArticleAboutApp }

}

