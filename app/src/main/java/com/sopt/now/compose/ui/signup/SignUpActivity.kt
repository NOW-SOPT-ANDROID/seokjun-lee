package com.sopt.now.compose.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.login.SIGNUP_KEY
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

private const val TAG = "SignUpActivity"
class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 30.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen(
                        onClick = {id, pw, nickname, mbti->
                            onClickLoginButton(id = id, pw = pw, nickname = nickname, mbti = mbti)
                        }
                    )
                }
            }
        }
    }

    private fun onClickLoginButton(id: String, pw:String, nickname: String, mbti: String){
        val userData = User(id = id, pw = pw, nickName = nickname, mbti = mbti)
        if(checkSignUp(userData = userData)) {
            val intent = Intent()
            intent.putExtra(SIGNUP_KEY, userData)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun checkSignUp(userData: User): Boolean = when {
        userData.id.length !in 6..10 -> {
            Toast.makeText(this, "ID는 6~10 글자", Toast.LENGTH_SHORT).show()
            false
        }
        userData.pw.length !in 8..12 ->{
            Toast.makeText(this, "비밀번호는 8~12 글자", Toast.LENGTH_SHORT).show()
            false
        }
        userData.nickName.isBlank() -> {
            Toast.makeText(this, "닉네임은 한 글자 이상, 공백 불가", Toast.LENGTH_SHORT).show()
            false
        }
        userData.mbti.isBlank() -> {
            Toast.makeText(this, "MBTI 공백 불가", Toast.LENGTH_SHORT).show()
            false
        }
        else -> {
            true
        }
    }
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onClick:(String, String, String, String) -> Unit
) {
    var id by remember {mutableStateOf("")}
    var pw by remember {mutableStateOf("")}
    var nickname by remember {mutableStateOf("")}
    var mbti by remember {mutableStateOf("")}

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val commonFontSize = 30.sp
        val commonModifier = Modifier.padding(top = 30.dp)

        Text(
            text = "SIGN UP",
            fontSize = 40.sp,
            color = Color.Black
        )

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = "ID",
            fontSize = commonFontSize,
            label = "아이디를 입력해주세요",
            textFieldText = id,
            onValueChange = {newValue ->
                id = newValue
            })

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = "비밀번호",
            fontSize = commonFontSize,
            label = "비밀번호를 입력해주세요",
            textFieldText = pw,
            onValueChange = {newValue ->
                pw = newValue
            })

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = "닉네임",
            fontSize = commonFontSize,
            label = "닉네임을 입력해주세요",
            textFieldText = nickname,
            onValueChange = {newValue ->
                nickname = newValue
            })

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = "MBTI",
            fontSize = commonFontSize,
            label = "MBTI를 입력해주세요",
            textFieldText = mbti,
            imeAction = ImeAction.Done,
            onValueChange = {newValue ->
                mbti = newValue
            })

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonComposable(
                text = "회원가입하기",
                onClick = { onClick(id, pw, nickname, mbti) })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpPreview() {
    SignUpScreen(onClick = { id, pw, nickname, mbti->})
}