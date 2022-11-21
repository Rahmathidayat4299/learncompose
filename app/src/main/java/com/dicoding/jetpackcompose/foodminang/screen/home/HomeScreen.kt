package com.dicoding.jetpackcompose.foodminang.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.dicoding.jetpackcompose.foodminang.Injection
import com.dicoding.jetpackcompose.foodminang.ViewModelFactory
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import com.dicoding.jetpackcompose.foodminang.screen.HomeViewModel
import com.dicoding.jetpackcompose.foodminang.ui.theme.FoodMinangTheme
import com.dicoding.jetpackcompose.foodminang.util.UiState

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Long) -> Unit,


) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getListFood()
                }
                is UiState.Success -> {
                    HomeContent(
                        orderReward = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                        onItemClicked = navigateToDetail
                    )
                }
                is UiState.Error -> {}
            }
        }
    }
}

//add
@Composable
fun FoodListItem(
    id:Long,
    name: String,
    photoUrl: String,
    onItemClicked: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            onItemClicked.invoke(id)
        }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun HomeContent(
    orderReward: List<FoodList>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onItemClicked: (id: Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderReward) { data ->
            FoodListItem(
                id = data.food.id,
                name = data.food.name,
                photoUrl = data.food.imageUrl,
                modifier = Modifier.clickable {
                    navigateToDetail(data.food.id)

                },
                onItemClicked = onItemClicked
            )

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FoodListItemPreview() {
//    FoodMinangTheme {
//        FoodListItem(
//            name = "Sala lauak",
//            photoUrl = "",
//            99,
//            onItemClicked = { Long, _ ->  },
//
//        )
//    }
//}