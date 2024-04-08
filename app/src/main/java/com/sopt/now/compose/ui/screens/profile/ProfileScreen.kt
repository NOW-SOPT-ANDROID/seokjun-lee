package com.sopt.now.compose.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.MainActivity.Companion.LOGIN_KEY
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.composables.ScreenWithImage
import com.sopt.now.compose.ui.composables.TitleAndContentTextComposable
import com.sopt.now.compose.ext.getDataFromPreviousBackStackEntry

private const val TAG = "ProfileScreen"

@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel()
) {
    navController.getDataFromPreviousBackStackEntry<User>(LOGIN_KEY)?.value?.let { user ->
        viewModel.updateUiState(user)
    }

    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) { paddingValue ->
        when (val uiState = viewModel.uiState.collectAsState().value) {
            is ProfileUiState.Success -> {
                ProfileScreen(user = uiState.user, modifier = Modifier.padding(paddingValue))
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
    user: User
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
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
                text = user.nickName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 10.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.main_mbti_introduction, user.mbti),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )


        TitleAndContentTextComposable(
            title = R.string.title_pw,
            content = user.id,
            modifier = Modifier.padding(top = 70.dp)
        )
        TitleAndContentTextComposable(
            title = R.string.title_pw,
            content = user.pw,
            modifier = Modifier.padding(top = 70.dp)
        )
    }
}
