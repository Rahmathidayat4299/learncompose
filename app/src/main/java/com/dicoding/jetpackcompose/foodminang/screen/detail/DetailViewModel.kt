package com.dicoding.jetpackcompose.foodminang.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import com.dicoding.jetpackcompose.foodminang.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
class DetailViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FoodList>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FoodList>>
        get() = _uiState

    fun getDetailFoodById(foodId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getlistFoodById(foodId))
        }
    }
}