package com.sopt.now.compose.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.signup.SignUpActivity
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

const val SIGNUP_KEY = "user"
const val LOGIN_KEY = "login"

class LoginActivity : ComponentActivity() {
    private val users: MutableList<User> = mutableListOf()

    private val getSignUpResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(SIGNUP_KEY, User::class.java)
            } else {
                result.data?.getSerializableExtra(SIGNUP_KEY)
            }
            userData?.let { users.add(it as User) }
            Toast.makeText(this, "회원가입이 완료되었습니다.}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onClickLogin = {id, pw ->
                            val index = checkIdAndPw(id, pw)
                            if (index != null) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra(LOGIN_KEY, users[index])
                                startActivity(intent)
                            }
                        },
                        onClickSignUp = {
                            val intent = Intent(this, SignUpActivity::class.java)
                            getSignUpResult.launch(intent)
                        })
                }
            }
        }
    }

    private fun checkIdAndPw(id: String, pw: String): Int? {
        var answer: Int? = null
        var toastMessage = ""
        when{
            id.isBlank() -> {toastMessage = "아이디를 입력해주세요"}
            pw.isBlank() -> {toastMessage = "비밀번호를 입력해주세요"}
            else -> {
                for (index in users.indices) {
                    if (users[index].id == id) {
                        if (users[index].pw == pw) {
                            answer = index
                            toastMessage = "로그인에 성공했습니다."
                            break
                        } else {
                            toastMessage = "비밀번호를 다시 확인하세요"
                        }
                    } else {
                        toastMessage = "아이디를 다시 확인하세요"
                    }
                }
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        return answer
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