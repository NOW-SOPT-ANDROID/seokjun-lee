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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.R
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
            Toast.makeText(this, getString(R.string.login_toast_success_signup), Toast.LENGTH_SHORT).show()
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
        var toastMessageResId:Int = R.string.login_toast_blank_id
        when{
            id.isBlank() -> {toastMessageResId = R.string.login_toast_blank_id}
            pw.isBlank() -> {toastMessageResId = R.string.login_toast_blank_pw}
            else -> {
                for (index in users.indices) {
                    if (users[index].id == id) {
                        if (users[index].pw == pw) {
                            answer = index
                            toastMessageResId = R.string.login_toast_success_login
                            break
                        } else {
                            toastMessageResId = R.string.login_toast_check_pw
                        }
                    } else {
                        toastMessageResId = R.string.login_toast_check_id
                    }
                }
            }
        }

        Toast.makeText(this, getString(toastMessageResId), Toast.LENGTH_SHORT).show()
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
            text = stringResource(id = R.string.login_title),
            fontSize = 30.sp
        )

        Column {
            TextFieldWithTitleComposable(
                title = stringResource(id = R.string.title_id),
                label = stringResource(id = R.string.login_label_id),
                textFieldText = id,
                onValueChange = { newValue ->
                    id = newValue
                })
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithTitleComposable(
                title = stringResource(id = R.string.title_pw),
                label = stringResource(id = R.string.login_label_pw),
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
                text = stringResource(id = R.string.login_btn_login),
                onClick = { onClickLogin(id, pw) },
                color = Color.Unspecified)
            ButtonComposable(
                text = stringResource(id = R.string.login_btn_signup),
                onClick = onClickSignUp,
                color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onClickLogin = {_, _ ->})
}