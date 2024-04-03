package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.TitleAndContentTextComposable
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(user = User())
                }
            }
        }
    }
}

@Composable
fun MainScreen(user: User) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.weight(1f),
                alignment = Alignment.CenterStart
            )
            Text(
                text = user.nickName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 10.dp))
        }

        Text(
            text = "제 MBTI는 ${user.mbti}입니다!!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp))


        TitleAndContentTextComposable(title = "ID", content = user.id, modifier = Modifier.padding(top = 70.dp))
        TitleAndContentTextComposable(title = "비밀번호", content = user.pw,modifier = Modifier.padding(top = 70.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NOWSOPTAndroidTheme {
        MainScreen(user = User("a", "b", "c", "dddd"))
    }
}