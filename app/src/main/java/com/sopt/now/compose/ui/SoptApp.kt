package com.sopt.now.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.ui.navigation.ApplicationNavHost
import com.sopt.now.compose.ui.navigation.HomeDestination
import com.sopt.now.compose.ui.navigation.NavigationDestination
import com.sopt.now.compose.ui.navigation.ProfileDestination
import com.sopt.now.compose.ui.navigation.SearchDestination

@Composable
fun SoptApp() {
    Surface {
        ApplicationNavHost(
            navController = rememberNavController(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SoptBottomNavigation(
    items: List<NavigationDestination> = listOf(HomeDestination, SearchDestination, ProfileDestination),
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        containerColor = Color.LightGray,
    ) {
        // items 배열에 담긴 모든 항목을 추가합니다.
        items.forEach { item ->
            // 뷰의 활동 상태를 백스택에 담아 저장합니다.
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.currentBackStackEntry?.destination?.route!!)
                        { inclusive = true }
                    }
                },
                icon = {
                    item.iconVector?.let {imageVector ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = imageVector,
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        }
                    }
                }
            )
        }
    }
}