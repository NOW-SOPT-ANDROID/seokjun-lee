package com.sopt.now.compose.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {
    /**
     * Unique name to define the path for a screen composable
     */
    val route: String
    val titleRes: Int
    val iconVector: ImageVector?
}