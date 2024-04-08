package com.sopt.now.compose.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.MainActivity
import com.sopt.now.compose.R
import com.sopt.now.compose.models.Friend
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.composables.ScreenWithImage
import com.sopt.now.compose.ext.getDataFromPreviousBackStackEntry

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel()
) {
    navController.getDataFromPreviousBackStackEntry<User>(MainActivity.LOGIN_KEY)?.value?.let { user ->
        viewModel.updateUiState(user)
    }

    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) { paddingValue ->
        when (val uiState = viewModel.uiState.collectAsState().value) {
            is HomeUiState.Success -> {
                HomeScreen(uiState = uiState, friendList = viewModel.mockFriendList, modifier = Modifier.padding(paddingValue))
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
        modifier = modifier
    ) {
        items(friendList) {friend ->
            FriendItemComposable(
                imageRes = R.drawable.ic_launcher_background,
                contentDescription = "",
                name = friend.name,
                selfDescription = friend.selfDescription
            )
        }
    }
}

@Composable
fun FriendItemComposable(
    @DrawableRes
    imageRes: Int,
    contentDescription: String,
    name: String,
    selfDescription: String

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(70.dp)
                .aspectRatio(1f),
            painter = painterResource(id = imageRes),
            contentDescription = contentDescription
        )
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ){
            Text(
                text = name,
                fontSize = 15.sp,
                maxLines = 1
            )
            Text(
                text = selfDescription,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    FriendItemComposable(
        imageRes = R.drawable.ic_launcher_background,
        contentDescription = "",
        name = "aaaa",
        selfDescription = "bbbb")
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}