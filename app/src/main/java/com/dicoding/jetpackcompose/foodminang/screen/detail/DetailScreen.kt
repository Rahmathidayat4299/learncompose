package com.dicoding.jetpackcompose.foodminang.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetpackcompose.foodminang.Injection
import com.dicoding.jetpackcompose.foodminang.ViewModelFactory
import com.dicoding.jetpackcompose.foodminang.ui.theme.FoodMinangTheme
import com.dicoding.jetpackcompose.foodminang.util.UiState

/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
@Composable
fun DetailScreen(
    foodId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDetailFoodById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.food.imageUrl,
                    data.food.name,
                    data.food.description,
                )
            }
            is UiState.Error -> {}
        }
    }
}
@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    ) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    FoodMinangTheme {
        DetailContent(
            "Jaket Hoodie Dicoding",
            "7500",
            "1"
        )
    }
}