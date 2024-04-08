package com.sopt.now.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sopt.now.compose.ui.screens.home.HomeScreen
import com.sopt.now.compose.ui.screens.login.LoginScreen
import com.sopt.now.compose.ui.screens.profile.ProfileScreen
import com.sopt.now.compose.ui.screens.search.SearchScreen
import com.sopt.now.compose.ui.screens.signup.SignUpScreen

@Composable
fun ApplicationNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(
            route = LoginDestination.route,
        ) {
            LoginScreen(
                navController = navController,
            )
        }
        composable(
            route = SignUpDestination.route,
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = HomeDestination.route,
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = SearchDestination.route,
        ) {
            SearchScreen(navController = navController)
        }
        composable(
            route = ProfileDestination.route,
        ) {
            ProfileScreen(navController = navController)
        }
    }
}
