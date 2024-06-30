package com.sopt.now.compose.presentation.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.presentation.R
import com.sopt.now.compose.presentation.composables.ButtonComposable
import com.sopt.now.compose.presentation.composables.ScreenWithImage
import com.sopt.now.compose.presentation.composables.TitleAndContentTextComposable
import com.sopt.now.compose.presentation.screens.search.SearchViewModel.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.presentation.SoptBottomNavigation

@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    LaunchedEffect(navController) {
        //이 부분을 SharedPreference를 확인하는 코드로 수정
        /*val memberId = navController.previousBackStackEntry?.savedStateHandle
            ?.getLiveData<String>(NAVIGATE_LOGIN_KEY)?.value
        if(memberId != null) {
            viewModel.getUserInfo()
        }*/
        viewModel.getUserInfo()
    }

    BackHandler {
        navController.run {
            previousBackStackEntry?.savedStateHandle?.set(NAVIGATE_BACK_PRESSED_KEY, NAVIGATE_BACK_PRESSED_KEY)
            navigateUp()
        }
    }

    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) { paddingValue ->
        when (val uiState = viewModel.uiState.collectAsStateWithLifecycle(LocalLifecycleOwner.current).value) {
            is ProfileUiState.Success -> {
                ProfileScreen(
                    uiState = uiState,
                    onLogoutButtonPressed = {navController.navigateUp()},
                    modifier = Modifier.padding(paddingValue))
            }

            is ProfileUiState.Loading -> {
                ScreenWithImage(imageRes = R.drawable.loading_img, contentDescription = "Loading")
            }

            is ProfileUiState.Error -> {
                ScreenWithImage(imageRes = R.drawable.ic_broken_image, contentDescription = "Error")
            }
        }
    }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState.Success,
    onLogoutButtonPressed:() -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
            }
            Text(
                text = uiState.nickname,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 10.dp)
            )
        }

        TitleAndContentTextComposable(
            title = R.string.title_id,
            content = uiState.authenticationId,
            modifier = Modifier.padding(top = 70.dp)
        )
        TitleAndContentTextComposable(
            title = R.string.title_phone,
            content = uiState.phone,
            modifier = Modifier.padding(top = 70.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonComposable(
                text = R.string.profile_btn_logout,
                onClick = onLogoutButtonPressed)
        }
    }
}

