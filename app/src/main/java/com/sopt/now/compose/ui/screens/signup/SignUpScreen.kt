package com.sopt.now.compose.ui.screens.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainActivity.Companion.SIGNUP_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.navigation.putDataAtPreviousBackStackEntry

object SignUpDestination: NavigationDestination {
    override val route: String = "signup"
    override val titleRes: Int =  R.string.destination_title_signup
    override val iconVector: ImageVector? = null
}

@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var id by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }
    val context = LocalContext.current

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
            title = R.string.title_id,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_id,
            textFieldText = id,
            onValueChange = { newValue ->
                id = newValue
            })

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = R.string.title_pw,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_pw,
            textFieldText = pw,
            onValueChange = { newValue ->
                pw = newValue
            })

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_nickname,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_nickname,
            textFieldText = nickname,
            onValueChange = { newValue ->
                nickname = newValue
            })

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_nickname,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_mbti,
            textFieldText = mbti,
            imeAction = ImeAction.Done,
            onValueChange = { newValue ->
                mbti = newValue
            })

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonComposable(
                text = R.string.signup_btn_signup,
                onClick = {
                    val userData = User(id = id, pw = pw, nickName = nickname, mbti = mbti)
                    if (checkSignUp(context = context, userData = userData)) {
                        navController.putDataAtPreviousBackStackEntry(SIGNUP_KEY, userData)
                        navController.navigateUp()
                    }
                })
        }
    }
}

private fun checkSignUp(context: Context, userData: User): Boolean {
    var toastStringRes: Int? = null
    val isPossible = when {
        userData.id.length !in 6..10 -> {
            toastStringRes = R.string.signup_toast_id
            false
        }

        userData.pw.length !in 8..12 -> {
            toastStringRes = R.string.signup_toast_pw
            false
        }

        userData.nickName.isBlank() -> {
            toastStringRes = R.string.signup_toast_nickname
            false
        }

        userData.mbti.isBlank() -> {
            toastStringRes = R.string.signup_toast_mbti
            false
        }

        else -> {
            true
        }
    }
    if(toastStringRes != null) {
        Toast.makeText(context, getString(context, toastStringRes), Toast.LENGTH_SHORT).show()
    }
    return isPossible
}