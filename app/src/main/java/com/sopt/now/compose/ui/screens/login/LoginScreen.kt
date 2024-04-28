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
import com.sopt.now.compose.MainActivity.Companion.printToastMessage
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.navigation.SignUpDestination

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    viewModel.checkCurrentStack(context = context, navController = navController)

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
                textFieldText = uiState.value.id,
                onValueChange = { viewModel.updateUiState(id = it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithTitleComposable(
                title = R.string.title_pw,
                label = R.string.login_label_pw,
                textFieldText = uiState.value.pw,
                onValueChange = { viewModel.updateUiState(pw = it) },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Column {
            ButtonComposable(
                text = R.string.login_btn_login,
                onClick = {
                    printToastMessage(context = context, viewModel.getToastMessageByCheckingIdAndPw())
                    viewModel.onLoginButtonClicked(navController)
                },
                color = Color.Unspecified
            )

            ButtonComposable(
                text = R.string.login_btn_signup,
                onClick = { viewModel.onSignUpButtonClicked(navController) },
                color = Color.Gray
            )
        }
    }
}


