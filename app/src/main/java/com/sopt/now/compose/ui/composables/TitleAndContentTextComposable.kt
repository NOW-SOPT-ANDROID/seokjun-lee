package com.sopt.now.compose.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleAndContentTextComposable(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 30.sp
        )
        Text(
            text = content,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}