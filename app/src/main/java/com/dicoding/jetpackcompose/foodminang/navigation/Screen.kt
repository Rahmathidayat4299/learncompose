package com.dicoding.jetpackcompose.foodminang.navigation

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
}
