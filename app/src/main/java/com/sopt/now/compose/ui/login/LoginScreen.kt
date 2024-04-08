package com.sopt.now.compose.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.home.HomeDestination
import com.sopt.now.compose.ui.home.HomeScreen
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.signup.SignUpDestination

object LoginDestination: NavigationDestination {
    override val route: String = "login"
    override val titleRes: Int = R.string.destination_title_login
    override val iconVector: ImageVector? = null
}

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                title = R.string.title_id,
                label = R.string.login_label_id,
                textFieldText = id,
                onValueChange = { newValue ->
                    id = newValue
                })
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithTitleComposable(
                title = R.string.title_pw,
                label = R.string.login_label_pw,
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
                text = R.string.login_btn_login,
                onClick = {
                    checkIdAndPw(
                        context = context,
                        id = id,
                        pw = pw,
                        users = listOf(User("test", "test1234")),
                    )?.let { userIndex ->
                        navController.navigate(HomeDestination.route)
                    }
                },
                color = Color.Unspecified)

            ButtonComposable(
                text = R.string.login_btn_signup,
                onClick = {navController.navigate(SignUpDestination.route)},
                color = Color.Gray)
        }
    }
}

private fun checkIdAndPw(context: Context, users: List<User>, id: String, pw: String): Int? {
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

    Toast.makeText(context, getString(context, toastMessageResId), Toast.LENGTH_SHORT).show()
    return answer
}