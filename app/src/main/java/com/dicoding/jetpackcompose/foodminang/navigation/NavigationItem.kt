package com.dicoding.jetpackcompose.foodminang.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

