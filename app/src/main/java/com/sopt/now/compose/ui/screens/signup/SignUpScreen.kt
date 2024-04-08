package com.sopt.now.compose.ui.screens.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.MainActivity.Companion.SIGNUP_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.composables.ButtonComposable
import com.sopt.now.compose.ui.composables.TextFieldWithTitleComposable
import com.sopt.now.compose.ui.navigation.putDataAtPreviousBackStackEntry

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel:SignUpViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 30.dp, vertical = 50.dp),
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
            textFieldText = uiState.value.id,
            onValueChange = {viewModel.updateUiState(id = it)})

        TextFieldWithTitleComposable(
            modifier = commonModifier,
            title = R.string.title_pw,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_pw,
            textFieldText = uiState.value.pw,
            onValueChange = {viewModel.updateUiState(pw = it)})

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_nickname,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_nickname,
            textFieldText = uiState.value.nickName,
            onValueChange = { viewModel.updateUiState(nickName = it)})

        TextFieldWithTitleComposable(
            modifier = Modifier.padding(top = 30.dp),
            title = R.string.title_mbti,
            titleFontSize = commonFontSize,
            label = R.string.signup_label_mbti,
            imeAction = ImeAction.Done,
            textFieldText = uiState.value.mbti,
            onValueChange = {viewModel.updateUiState(mbti = it)})

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonComposable(
                text = R.string.signup_btn_signup,
                onClick = {
                    if (viewModel.checkSignUp(context = context)) {
                        navController.putDataAtPreviousBackStackEntry(SIGNUP_KEY, uiState.value)
                        navController.navigateUp()
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}