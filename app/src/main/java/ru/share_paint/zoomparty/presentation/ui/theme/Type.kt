package ru.share_paint.zoomparty.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import ru.tic_tac_toe.zoomparty.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val styleAboutText = TextStyle(
    fontSize = fontSizeSmall.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily.SansSerif,
    textAlign = TextAlign.Justify
)
val stylePost = TextStyle(
    fontSize = fontSizeSmall.sp,
    lineHeight = 24.sp,
    fontFamily = FontFamily.Monospace,
    textAlign = TextAlign.Justify,
    color = CianDark,
    textDecoration = TextDecoration.Underline
)
val styleAboutTextBold = TextStyle(
    fontSize = fontSizeSmall.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily.SansSerif,
    textAlign = TextAlign.Justify,
    fontWeight = FontWeight.Bold
)
val styleMinText = TextStyle(
    fontSize = fontSizeMin.sp,
    lineHeight = 12.sp,
    fontFamily = FontFamily.SansSerif,
    textAlign = TextAlign.Justify
)
val styleLargeText = TextStyle(
    fontSize = fontSizeLarge.sp,
    lineHeight = 16.sp,
    fontFamily = FontFamily.SansSerif,
    textAlign = TextAlign.Justify
)
val HonkFamily = FontFamily(
    Font(R.font.honk_regular, FontWeight.Normal),
)
val DownFamily = FontFamily(
    Font(R.font.down, FontWeight.Normal),
)
val KashmirFamily = FontFamily(
    Font(R.font.kashmir, FontWeight.Normal),
)
val FestaFamily = FontFamily(
    Font(R.font.festa_classica, FontWeight.Normal),
)
val GameStyle = TextStyle(
    fontFamily = FestaFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold
)
val GameStyleLarge = TextStyle(
    fontFamily = FestaFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold
)