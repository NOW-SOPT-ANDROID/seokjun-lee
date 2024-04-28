package com.sopt.now.compose.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ScreenWithImage(
    modifier: Modifier = Modifier,
    @DrawableRes
    imageRes: Int,
    contentDescription: String,
) {
    Box(modifier = modifier.fillMaxSize()){
        Image(painter = painterResource(id = imageRes), contentDescription = contentDescription)
    }
}