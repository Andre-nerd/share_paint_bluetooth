package ru.share_paint.zoomparty.presentation.sreens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.share_paint.zoomparty.presentation.navigation.Route
import ru.share_paint.zoomparty.presentation.ui.theme.fontSize
import ru.share_paint.zoomparty.presentation.ui.theme.largePadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.tic_tac_toe.zoomparty.R

@Composable
fun BriefInstructionOneDevice() {
    Card(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Игра на одном устройстве",
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier,
                text = "Если вы используете приложение индивидуально - пропустите настройки, нажмите на кнопку внизу экрана.",
                style = styleAboutText
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    modifier = Modifier
                        .scale(0.5f)
                        .clickable { },
                    painter = painterResource(id = R.drawable.color_radius), contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun BriefInstructionTwoDevice(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 132.dp),
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
                text = "Игра на двух устройствах",
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier,
                text = "Перед началом игры убедитесь, что устройства установили сопряжение по Bluetooth (подробней об этом можно прочитать в инструкции, в разделе Помощь).\n" +
                        "Первый игрок выбирает роль \"Мастер\" (Master)",
                style = styleAboutText
            )
            Spacer(modifier = Modifier.size(largePadding.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    modifier = Modifier
                        .scale(1f)
                        .clickable { },
                    painter = painterResource(id = R.drawable.help_master_slave_1_1), contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.size(largePadding.dp))
            Text(
                modifier = Modifier,
                text = "Второй игрок выбирает роль \"Участник\" (Participant).\n" +
                        "В разделе \"Подключиться к мастер-устройству\" выбирает устройство первого игрока.\n" +
                        "Можно начинать игру.\n" +
                        "При следующем запуске приложения, можно пропустить этот этап, так как предыдущие настройки уже сохранены в приложении.",
                style = styleAboutText
            )
            Spacer(modifier = Modifier.size(largePadding.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    modifier = Modifier
                        .scale(1f)
                        .clickable { },
                    painter = painterResource(id = R.drawable.help_master_slave_1_2), contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.size(largePadding.dp))
            Text(
                modifier = Modifier,
                text = "Больше информации можно получить в разделе \"Помощь\"",
                style = styleAboutText
            )
            Spacer(modifier = Modifier.size(largePadding.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    navController.navigate(Route.Help.name)
                }
                ) {
                    Text(text = "Помощь")
                }
            }
        }
    }
}

