package com.sopt.now.compose.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.MainActivity.Companion.LOGIN_KEY
import com.sopt.now.compose.MainActivity.Companion.SIGNUP_KEY
import com.sopt.now.compose.MainActivity.Companion.printToastMessage
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.navigation.ProfileDestination
import com.sopt.now.compose.ui.navigation.SignUpDestination
import com.sopt.now.compose.ui.navigation.getDataFromCurrentBackStackEntry
import com.sopt.now.compose.ui.navigation.putDataAtCurrentStackEntry

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val result = navController.getDataFromCurrentBackStackEntry<User>(SIGNUP_KEY)
    result?.value?.let { newUser ->
        viewModel.addUsers(newUser)
    }

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

        LoginTextFields(
            id = uiState.value.id,
            pw = uiState.value.pw,
            onIdChanged = { viewModel.updateUiState(id = it) },
            onPwChanged = { viewModel.updateUiState(pw = it) }
        )

        LoginButtons(
            onClickLoginButton = {
                printToastMessage(context = context, viewModel.getToastMessageByCheckingIdAndPw())
                if (viewModel.isLoginPossible()) {
                    navController.putDataAtCurrentStackEntry(LOGIN_KEY, viewModel.getUser())
                    viewModel.updateUiState(id = "", pw = "", userIndex = -1)
                    navController.navigate(ProfileDestination.route)
                }
            },
            onClickSignUpButton = {
                navController.navigate(SignUpDestination.route)
            })
    }
}

@Composable
fun LoginTextFields(
    id: String,
    pw: String,
    onIdChanged: (String) -> Unit,
    onPwChanged: (String) -> Unit,
) {
    Column {
        TextFieldWithTitleComposable(
            title = R.string.title_id,
            label = R.string.login_label_id,
            textFieldText = id,
            onValueChange = onIdChanged
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldWithTitleComposable(
            title = R.string.title_pw,
            label = R.string.login_label_pw,
            textFieldText = pw,
            onValueChange = onPwChanged,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun LoginButtons(
    onClickLoginButton: () -> Unit,
    onClickSignUpButton: () -> Unit
) {
    Column {
        ButtonComposable(
            text = R.string.login_btn_login,
            onClick = onClickLoginButton,
            color = Color.Unspecified
        )

        ButtonComposable(
            text = R.string.login_btn_signup,
            onClick = onClickSignUpButton,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}



