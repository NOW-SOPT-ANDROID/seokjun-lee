package com.sopt.now.compose.ui.screens.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.MainActivity.Companion.printToastMessage
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.navigation.HomeDestination
import com.sopt.now.compose.ui.navigation.SignUpDestination

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    checkCurrentStack(context = context, navController = navController)

    LaunchedEffect(uiState.message) {
        if(uiState.message.isNotEmpty()){
            Log.d("SignUpScreen", "launched")
            if(uiState.isSuccess) {
                Toast.makeText(context, "로그인 성공 ${uiState.message}", Toast.LENGTH_SHORT).show()
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = MainActivity.NAVIGATE_LOGIN_KEY,
                    value = uiState.message
                )
                navController.navigate(HomeDestination.route)
                viewModel.updateUiState(id = "", pw = "", userIndex = -1)
            } else {
                Toast.makeText(context, "로그인 실패 ${uiState.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onLoginButtonClick = {viewModel.postLogin()},
        onSignUpButtonClick = {navController.navigate(SignUpDestination.route)},
        onIdTextFieldChanged = {viewModel.updateUiState(id = it)},
        onPwTextFieldChanged = {viewModel.updateUiState(pw = it)}
    )

}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onIdTextFieldChanged:(String) -> Unit,
    onPwTextFieldChanged:(String) -> Unit,
    onLoginButtonClick:() -> Unit,
    onSignUpButtonClick:() -> Unit,
) {
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
                textFieldText = uiState.id,
                onValueChange = onIdTextFieldChanged
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithTitleComposable(
                title = R.string.title_pw,
                label = R.string.login_label_pw,
                textFieldText = uiState.pw,
                onValueChange = onPwTextFieldChanged,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Column {
            ButtonComposable(
                text = R.string.login_btn_login,
                onClick = onLoginButtonClick,
                color = Color.Unspecified
            )

            ButtonComposable(
                text = R.string.login_btn_signup,
                onClick = onSignUpButtonClick,
                color = Color.Gray
            )
        }
    }
}

private fun checkCurrentStack(context: Context, navController: NavHostController) {
    navController.currentBackStackEntry?.savedStateHandle?.run {
        getLiveData<String>(MainActivity.NAVIGATE_BACK_PRESSED_KEY).value?.run{
            if(this == MainActivity.NAVIGATE_BACK_PRESSED_KEY) {
                if (context is Activity) {
                    context.finish()
                }
            }
        }
    }
}


