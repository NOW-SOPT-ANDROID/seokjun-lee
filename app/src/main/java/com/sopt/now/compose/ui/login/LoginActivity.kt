package com.sopt.now.compose.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.Greeting
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    onClickLogin: (String, String) -> Unit,
    onClickSignUp: () -> Unit = {}
) {
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Welcome to SOPT",
            fontSize = 30.sp
        )

        Column {
            TextFieldWithTitleComposable(
                title = "ID",
                label = "사용자 이름 입력",
                textFieldText = id,
                onValueChange = { newValue ->
                    id = newValue
                })
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithTitleComposable(
                title = "비밀번호",
                label = "비밀번호 입력",
                textFieldText = pw,
                onValueChange = { newValue ->
                    pw = newValue
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Column {
            ButtonComposable(
                text = "로그인",
                onClick = { onClickLogin(id, pw) },
                color = Color.Unspecified
            )
            ButtonComposable(text = "회원가입", onClick = onClickSignUp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onClickLogin = {_, _ ->})
}