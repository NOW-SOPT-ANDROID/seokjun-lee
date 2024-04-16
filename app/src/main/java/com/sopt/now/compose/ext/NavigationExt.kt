package com.sopt.now.compose.ext

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder


fun NavOptionsBuilder.removePreviousBackStacks(navController: NavHostController, screenInclusive: Boolean = true) {
    popUpTo(navController.currentBackStackEntry?.destination?.route!!)
    { inclusive = screenInclusive }
}

