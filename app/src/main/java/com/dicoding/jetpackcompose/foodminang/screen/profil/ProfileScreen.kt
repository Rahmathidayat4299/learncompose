package com.dicoding.jetpackcompose.foodminang


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            AsyncImage(model = "https://raw.githubusercontent.com/Rahmathidayat4299/asset_compose_dicoding/main/rahmathidayat.png", contentDescription ="profil" )
            Text(stringResource(R.string.menu_profile))
            Text(stringResource(R.string.nama))
            Text(stringResource(R.string.email))

        }
    }
}