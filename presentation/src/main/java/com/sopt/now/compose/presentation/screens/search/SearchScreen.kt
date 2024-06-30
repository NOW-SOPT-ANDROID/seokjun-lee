package com.sopt.now.compose.presentation.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sopt.now.compose.presentation.screens.search.SearchViewModel.Companion.NAVIGATE_BACK_PRESSED_KEY
import com.sopt.now.compose.presentation.SoptBottomNavigation


/**
 * 구현 예정
 */
@Composable
fun SearchScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = { SoptBottomNavigation(navController = navController) }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ){
            Text(text = "search")
        }
    }

    BackHandler {
        navController.run {
            previousBackStackEntry?.savedStateHandle?.set(NAVIGATE_BACK_PRESSED_KEY, NAVIGATE_BACK_PRESSED_KEY)
            navigateUp()
        }
    }
}