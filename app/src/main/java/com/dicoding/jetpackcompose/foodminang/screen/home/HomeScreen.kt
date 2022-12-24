package com.dicoding.jetpackcompose.foodminang.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetpackcompose.foodminang.*
import com.dicoding.jetpackcompose.foodminang.R
import com.dicoding.jetpackcompose.foodminang.model.Food
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import com.dicoding.jetpackcompose.foodminang.screen.HomeViewModel
import com.dicoding.jetpackcompose.foodminang.util.UiState

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Long) -> Unit,
) {
    val groupedHeroes by viewModel.groupedHeroes.collectAsState()
    val query by viewModel.query
    Box(modifier = modifier) {

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getListFood()

                }
                is UiState.Success -> {

                    HomePage(
                        query = query,
                        onQueryChange = viewModel::search,
                        orderReward = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                        onItemClicked = navigateToDetail,

                    )
                }
                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun HomePage(
    query: String,
    onQueryChange: (String) -> Unit,
    orderReward: List<FoodList>,
    onItemClicked: (id: Long) -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        SearchView(
            query = query,
            onQueryChange = onQueryChange
        )
        if (orderReward.isNotEmpty()) {
            HomeContent(
                orderReward = orderReward,
                onItemClicked = onItemClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyContent(
                contentText = stringResource(R.string.empty_content),
                modifier = Modifier
                    .testTag("empty_data")
            )
        }
    }
}

@Composable
fun HomeContent(
    orderReward: List<FoodList>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onItemClicked: (id: Long) -> Unit,
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {

        items(orderReward) { data ->
            CardFood(
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

//@Composable
//fun SearchBar(
//    query: String,
//    onQueryChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TextField(
//        value = query,
//        onValueChange = onQueryChange,
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = null
//            )
//        },
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = MaterialTheme.colors.surface,
//            disabledIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        placeholder = {
//            Text(stringResource(R.string.search_food))
//        },
//        modifier = modifier
//            .padding(16.dp)
//            .fillMaxWidth()
//            .heightIn(min = 48.dp)
//            .clip(RoundedCornerShape(16.dp))
//    )
//}

@Composable
fun FoodTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            //matchparent
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = modifier
                .size(64.dp)
                .padding(8.dp),
            model = "https://raw.githubusercontent.com/Rahmathidayat4299/asset_compose_dicoding/main/rumah-gadang.png",
            /*
             * Content Description is not needed here - image is decorative, and setting a null
             * content description allows accessibility services to skip this element during
             * navigation.
             */
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h6
        )
    }
}
