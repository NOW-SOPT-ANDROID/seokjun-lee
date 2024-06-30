package com.sopt.now.compose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.sopt.now.compose.presentation.R

object LoginDestination: NavigationDestination {
    override val route: String = "login"
    override val titleRes: Int = R.string.destination_title_login
    override val iconVector: ImageVector? = null
}

object SignUpDestination: NavigationDestination {
    override val route: String = "signup"
    override val titleRes: Int =  R.string.destination_title_signup
    override val iconVector: ImageVector? = null
}

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.destination_title_home
    override val iconVector: ImageVector = Icons.Filled.Home
}

object ProfileDestination: NavigationDestination{
    override val route: String = "profile"
    override val titleRes: Int = R.string.destination_route_profile
    override val iconVector: ImageVector = Icons.Filled.Person
}

object SearchDestination: NavigationDestination {
    override val route: String = "search"
    override val titleRes: Int = R.string.destination_route_search
    override val iconVector: ImageVector = Icons.Filled.Search
}