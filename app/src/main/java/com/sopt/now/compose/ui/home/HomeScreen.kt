package com.sopt.now.compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.sopt.now.compose.MainScreen
import com.sopt.now.compose.R
import com.sopt.now.compose.models.User
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.composables.TitleAndContentTextComposable
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme


object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.destination_title_home
    override val iconVector: ImageVector = Icons.Filled.Home
}

@Composable
fun HomeScreen(
    navController:NavHostController,
    user: User = User("hello", "hello")
) {
    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ){paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

        }

    }
}