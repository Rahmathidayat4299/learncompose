package com.dicoding.jetpackcompose.foodminang

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.jetpackcompose.HomeScreen
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.navigation.NavigationItem
import com.dicoding.jetpackcompose.foodminang.navigation.Screen
import com.dicoding.jetpackcompose.foodminang.ui.theme.FoodMinangTheme
import com.dicoding.jetpackcompose.foodminang.viewmodel.FoodMinangViewModel
import com.dicoding.jetpackcompose.foodminang.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodMinangApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: FoodMinangViewModel = viewModel(factory = ViewModelFactory(FoodRepository()))
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val groupedFood by viewModel.groupFood.collectAsState()
    val query by viewModel.query
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
//        LazyColumn(
//            state = listState,
//            contentPadding = PaddingValues(bottom = 80.dp)
//        ) {
//            item {
//                SearchBar(
//                    query = query,
//                    onQueryChange = viewModel::search,
//                    modifier = Modifier.background(MaterialTheme.colors.primary)
//                )
//            }
//            items(listFood, key = { it.id }) { food ->
//                FoodListItem(
//                    name = food.name,
//                    photoUrl = food.imageUrl,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .animateItemPlacement(tween(durationMillis = 100))
//
//                )
//            }
//        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    FoodMinangTheme {
        FoodMinangApp()
    }
}


////add
//@Composable
//fun FoodListItem(
//    name: String,
//    photoUrl: String,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.clickable {}
//    ) {
//        AsyncImage(
//            model = photoUrl,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .padding(8.dp)
//                .size(60.dp)
//                .clip(CircleShape)
//        )
//        Text(
//            text = name,
//            fontWeight = FontWeight.Medium,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//                .padding(start = 16.dp)
//        )
//    }
//}
@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(R.string.search_food))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
//bottom nav
@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),

            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun FoodListItemPreview() {
//    FoodMinangTheme {
//        FoodListItem(
//            name = "Sala lauak",
//            photoUrl = ""
//        )
//    }
//}