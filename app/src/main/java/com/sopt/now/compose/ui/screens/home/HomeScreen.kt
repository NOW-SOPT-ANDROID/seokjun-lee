package com.sopt.now.compose.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.R
import com.sopt.now.compose.models.Friend
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.composables.ScreenWithImage

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel(),
) {
    LaunchedEffect(navController){
        viewModel.fetchUserLoggedIn(navController)
    }

    BackHandler { viewModel.onBackPressed(navController) }

    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) { paddingValue ->
        when (val uiState = viewModel.uiState.collectAsState().value) {
            is HomeUiState.Success -> {
                HomeScreen(
                    uiState = uiState,
                    friendList = viewModel.mockFriendList,
                    modifier = Modifier.padding(paddingValue)
                )
            }

            is HomeUiState.Loading -> {
                ScreenWithImage(imageRes = R.drawable.loading_img, contentDescription = "Loading")
            }

            is HomeUiState.Error -> {
                ScreenWithImage(imageRes = R.drawable.ic_broken_image, contentDescription = "Error")
            }
        }

    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.Success,
    friendList: List<Friend>
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        item {
            ItemComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                imageRes = R.drawable.ic_launcher_background,
                contentDescription = "",
                imageSize = 80.dp,
                name = uiState.user.nickName,
                selfDescription = uiState.user.mbti,
                nameFontSize = 20.sp,
                selfDescriptionFontSize = 15.sp
            )

            Spacer(
                modifier = Modifier
                    .height(21.dp)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .background(color = Color.LightGray)
            )
        }
        items(friendList) { friend ->
            ItemComposable(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                imageRes = R.drawable.ic_launcher_background,
                contentDescription = "",
                name = friend.name,
                selfDescription = friend.selfDescription
            )
        }
    }
}

@Composable
fun ItemComposable(
    modifier: Modifier,
    @DrawableRes
    imageRes: Int,
    contentDescription: String,
    imageSize: Dp = 60.dp,
    name: String,
    nameFontSize: TextUnit = 15.sp,
    selfDescription: String,
    selfDescriptionFontSize: TextUnit = 12.sp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(imageSize)
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(20.dp)),
            painter = painterResource(id = imageRes),
            contentDescription = contentDescription
        )
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = name,
                fontSize = nameFontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = selfDescription,
                fontSize = selfDescriptionFontSize,
                maxLines = 1
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}