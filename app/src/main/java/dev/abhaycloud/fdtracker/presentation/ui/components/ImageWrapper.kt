package dev.abhaycloud.fdtracker.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

@Composable
fun ImageWrapper(resource: Int, modifier: Modifier = Modifier, colorFilter: ColorFilter? = null) {
    Image(
        modifier = modifier,
        painter = painterResource(id = resource),
        contentDescription = null,
        colorFilter = colorFilter
    )
}

@Composable
fun IconWrapper(resource: Int, modifier: Modifier = Modifier, tint: Color) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = resource),
        contentDescription = "icon",
        tint = tint
    )
}