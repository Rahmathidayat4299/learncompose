package com.dicoding.jetpackcompose.foodminang.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import com.dicoding.jetpackcompose.foodminang.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
class HomeViewModel(private val repository: FoodRepository):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FoodList>>> = MutableStateFlow(UiState.Loading)
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

}

