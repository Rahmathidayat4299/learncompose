package com.dicoding.jetpackcompose.foodminang.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.model.Food
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import com.dicoding.jetpackcompose.foodminang.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
class HomeViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FoodList>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FoodList>>>
        get() = _uiState

    fun getListFood() {
        viewModelScope.launch {
            repository.listFood()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { foodlist ->
                    _uiState.value = UiState.Success(foodlist)
                }
        }
    }


    private val _groupedHeroes = MutableStateFlow(
        repository.getFood()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedHeroes: StateFlow<Map<Char, List<Food>>> get() = _groupedHeroes
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedHeroes.value = repository.searchFood(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }

}

