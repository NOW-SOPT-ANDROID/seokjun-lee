package com.sopt.now.compose.presentation.screens.home

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sopt.now.compose.presentation.R
import com.sopt.now.compose.presentation.composables.ScreenWithImage
import com.sopt.now.compose.presentation.screens.search.SearchViewModel.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.presentation.SoptBottomNavigation

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(null) {
        viewModel.getNetworkData()
    }

    BackHandler {
        navController.run {
            previousBackStackEntry?.savedStateHandle?.set(
                NAVIGATE_BACK_PRESSED_KEY,
                NAVIGATE_BACK_PRESSED_KEY
            )
            navigateUp()
        }
    }

    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) { paddingValue ->
        val uiState = viewModel.uiState.collectAsStateWithLifecycle(LocalLifecycleOwner.current).value
        val uiStateFollower = viewModel.uiStateFollower.collectAsStateWithLifecycle(LocalLifecycleOwner.current).value


        when {
            uiState is HomeUiState.Success && uiStateFollower is FollowerUiState.Success  -> {
                HomeScreen(
                    uiState = uiState,
                    uiStateFollower = uiStateFollower,
                    modifier = Modifier.padding(paddingValue)
                )
            }

            uiState is HomeUiState.Loading || uiStateFollower is FollowerUiState.Loading -> {
                ScreenWithImage(imageRes = R.drawable.loading_img, contentDescription = "Loading")
            }

            uiState is HomeUiState.Error || uiStateFollower is FollowerUiState.Error -> {
                ScreenWithImage(imageRes = R.drawable.ic_broken_image, contentDescription = "Error")
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.Success,
    uiStateFollower: FollowerUiState.Success
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        item {
            ItemComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                imageUrl = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/roraima-bush-toad.png",
                contentDescription = "",
                imageSize = 80.dp,
                name = uiState.nickname,
                selfDescription = uiState.phone,
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
        items(uiStateFollower.follower) { follow ->
            ItemComposable(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                imageUrl = follow.avatar,
                contentDescription = "",
                name = "${follow.firstName} ${follow.lastName}",
                selfDescription = follow.email
            )
        }
    }
}

@Composable
internal fun UserInfoComposable(
    uiState: HomeUiState.Success
) {
    Column {
        ItemComposable(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            imageUrl = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/roraima-bush-toad.png",
            contentDescription = "",
            imageSize = 80.dp,
            name = uiState.nickname,
            selfDescription = uiState.phone,
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
}

@Composable
fun ItemComposable(
    modifier: Modifier,
    imageUrl: String,
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_broken_image),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(imageSize)
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(20.dp))
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

@Composable
internal fun FollowerComposable(

) {

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}