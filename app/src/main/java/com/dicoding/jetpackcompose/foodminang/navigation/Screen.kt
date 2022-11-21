package com.dicoding.jetpackcompose.foodminang.navigation

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailScreen : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
    object Profile : Screen("profile")
}
