package com.sopt.now.compose.ui.navigation

import android.provider.ContactsContract.Profile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sopt.now.compose.ui.home.HomeDestination
import com.sopt.now.compose.ui.home.HomeScreen
import com.sopt.now.compose.ui.login.LoginDestination
import com.sopt.now.compose.ui.login.LoginScreen
import com.sopt.now.compose.ui.profile.ProfileDestination
import com.sopt.now.compose.ui.profile.ProfileScreen
import com.sopt.now.compose.ui.search.SearchDestination
import com.sopt.now.compose.ui.search.SearchScreen
import com.sopt.now.compose.ui.signup.SignUpDestination
import com.sopt.now.compose.ui.signup.SignUpScreen

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
