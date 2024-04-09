package ru.share_paint.zoomparty.presentation.sreens.setting


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.share_paint.zoomparty.presentation.ui.theme.FestaFamily
import ru.share_paint.zoomparty.presentation.ui.theme.GameStyle
import ru.tic_tac_toe.zoomparty.R

@Composable
fun SelectGameOnceTwice(
    onceGame:()->Unit,
    twiceGame:()->Unit,
    ) {
    Dialog(
        onDismissRequest = { onceGame },
    ) {
        Card(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(stringResource(R.string.select_type_of_game), fontFamily = FestaFamily, fontSize = 24.sp)
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(
                        onClick = { onceGame.invoke() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally ) {
                            Text(stringResource(id = R.string.once_session),style = GameStyle)
                            Image(painter = painterResource(id = R.drawable.one_person), contentDescription = "One person" )
                        }

                    }
                    TextButton(
                        onClick = {
                            twiceGame.invoke()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally ) {
                            Text(stringResource(id = R.string.twice_session), style = GameStyle)
                            Image(painter = painterResource(id = R.drawable.two_person), contentDescription = "Two person" )
                        }
                    }
                }
            }
        }
    }
}
