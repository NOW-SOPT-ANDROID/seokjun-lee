package com.sopt.now.compose.ui.screens.search

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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sopt.now.compose.R
import com.sopt.now.compose.ui.SoptBottomNavigation
import com.sopt.now.compose.ui.navigation.NavigationDestination

object SearchDestination: NavigationDestination {
    override val route: String = "search"
    override val titleRes: Int = R.string.destination_route_search
    override val iconVector: ImageVector = Icons.Filled.Search
}

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
}