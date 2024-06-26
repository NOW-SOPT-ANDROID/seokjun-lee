package com.sopt.now.compose.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable

@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(LocalLifecycleOwner.current)
    LaunchedEffect(uiState.message) {
        if(uiState.message.isNotEmpty()){
            if(uiState.isSuccess) {
                Toast.makeText(context, "회원가입 성공 ${uiState.message}", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            } else {
                Toast.makeText(context, "회원가입 실패 ${uiState.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    SignUpScreen(
        uiState = uiState,
        onIdTextFieldChange = { viewModel.updateUiState(authenticationId = it) },
        onPwTextFieldChange = { viewModel.updateUiState(password = it) },
        onNickNameTextFieldChange = { viewModel.updateUiState(nickName = it) },
        onPhoneTextFieldChange = { viewModel.updateUiState(phone = it) },
        onSignUpButtonClick = { viewModel.patchSignUp() }
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpState,
    onIdTextFieldChange:(String) -> Unit,
    onPwTextFieldChange:(String) -> Unit,
    onNickNameTextFieldChange:(String) -> Unit,
    onPhoneTextFieldChange:(String) -> Unit,
    onSignUpButtonClick:() -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val commonFontSize = 30.sp
        val commonModifier = Modifier.padding(top = 30.dp)

        Text(
            text = stringResource(id = R.string.signup_title),
            fontSize = 40.sp,
            color = Color.Black
        )

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = R.string.title_id,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_id,
            textFieldText = uiState.authenticationId,
            onValueChange = onIdTextFieldChange)

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = R.string.title_pw,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_pw,
            textFieldText = uiState.password,
            onValueChange = onPwTextFieldChange)

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_nickname,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_nickname,
            textFieldText = uiState.nickName,
            onValueChange = onNickNameTextFieldChange)

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_phone,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_phone,
            imeAction = ImeAction.Done,
            textFieldText = uiState.phone,
            onValueChange = onPhoneTextFieldChange)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonComposable(
                text = R.string.signup_btn_signup,
                onClick = onSignUpButtonClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}