package com.dicoding.jetpackcompose.foodminang.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.dicoding.jetpackcompose.foodminang.ui.theme.FoodMinangTheme
import androidx.navigation.NavController
import com.dicoding.jetpackcompose.foodminang.navigation.Screen
import kotlinx.coroutines.delay

/**
 * Created by Rahmat Hidayat on 23/11/2022.
 */
@Composable
fun SplashScreenAnimated(navController: NavController){
    var startSplashScreen by remember{ mutableStateOf(false) }
    val alphanim = animateFloatAsState(
        targetValue =  if(startSplashScreen) 1f else 0f,
        animationSpec = tween(
            durationMillis =  4000
        )
    )
    LaunchedEffect(key1 = true ){
        startSplashScreen = true
        delay(4000)
        navController.navigate(Screen.Home.route)
    }
    Splash(alpha = alphanim.value)
}

@Composable
fun Splash(alpha:Float) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        AsyncImage(
            model = "https://raw.githubusercontent.com/Rahmathidayat4299/asset_compose_dicoding/main/rumah-gadang.png",
            contentDescription = "Image")

    }
}
@Composable
@Preview(showBackground = true)

fun SplashScreenView() {
    FoodMinangTheme {
        Splash(
        alpha = 1f
        )
    }
}
