package com.sopt.now.compose.ui.screens.search

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sopt.now.compose.R
import com.sopt.now.compose.ext.putDataAtPreviousBackStackEntry
import com.sopt.now.compose.ext.removePreviousBackStacks
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.navigation.LoginDestination
import com.sopt.now.compose.ui.navigation.NavigationDestination


/**
 * 구현 예정
 */
@Composable
fun SearchScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {SoptBottomNavigation(navController = navController)}
    ) {
        Column(
            modifier = Modifier.padding(it)
        ){
            Text(text = "search")
        }
    }

    BackHandler {
        navController.putDataAtPreviousBackStackEntry("back", "back")
        navController.navigateUp()
    }
}