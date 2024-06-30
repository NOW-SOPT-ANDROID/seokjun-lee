package com.sopt.now.compose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.sopt.now.compose.presentation.ext.removePreviousBackStacks
import com.sopt.now.compose.presentation.navigation.ApplicationNavHost
import com.sopt.now.compose.presentation.navigation.HomeDestination
import com.sopt.now.compose.presentation.navigation.NavigationDestination
import com.sopt.now.compose.presentation.navigation.ProfileDestination
import com.sopt.now.compose.presentation.navigation.SearchDestination
import com.sopt.now.compose.presentation.ui.theme.NOWSOPTAndroidTheme

@Composable
fun SoptApp() {
    NOWSOPTAndroidTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Surface {
                ApplicationNavHost(
                    navController = rememberNavController(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun SoptBottomNavigation(
    items: List<NavigationDestination> = listOf(HomeDestination, SearchDestination, ProfileDestination),
    navController: NavHostController,
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
                        removePreviousBackStacks(navController)
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