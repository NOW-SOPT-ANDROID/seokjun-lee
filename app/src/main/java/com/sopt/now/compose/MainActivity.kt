package com.sopt.now.compose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.TitleAndContentTextComposable
import com.sopt.now.compose.ui.login.LOGIN_KEY
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 60.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getSerializableExtra(LOGIN_KEY, User::class.java)
                    } else {
                        intent.getSerializableExtra(LOGIN_KEY)
                    }
                    val user = if (data != null) {
                        data as User
                    } else User()
                    MainScreen(user = user)
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
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
            }
            Text(
                text = user.nickName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 10.dp))
        }

        Text(
            text = stringResource(id = R.string.main_mbti_introduction, user.mbti),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp))


        TitleAndContentTextComposable(
            title = stringResource(id = R.string.title_pw),
            content = user.id,
            modifier = Modifier.padding(top = 70.dp))
        TitleAndContentTextComposable(
            title = stringResource(id = R.string.title_pw),
            content = user.pw,
            modifier = Modifier.padding(top = 70.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NOWSOPTAndroidTheme {
        MainScreen(user = User("a", "b", "c", "dddd"))
    }
}