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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.share_paint.zoomparty.presentation.ui.theme.fontSize
import ru.share_paint.zoomparty.presentation.ui.theme.largePadding
import ru.share_paint.zoomparty.presentation.ui.theme.sUiPadding
import ru.share_paint.zoomparty.presentation.ui.theme.styleAboutText
import ru.tic_tac_toe.zoomparty.R


@Composable
fun BriefInstructionTwoDevice(callback: ()->Unit) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 32.dp),
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
                    text = stringResource(R.string.game_on_twice_devices),
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.first_gamer_became_master),
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
                    text = stringResource(R.string.second_gamer_became_slave),
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
                    text = stringResource(R.string.when_setting_ok_play),
                    style = styleAboutText
                )
            }
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp), horizontalArrangement = Arrangement.Center) {
        Button(onClick = {
            callback.invoke()
        }
        ) {
            Text(text = stringResource(R.string.start_play))
        }
    }
}

