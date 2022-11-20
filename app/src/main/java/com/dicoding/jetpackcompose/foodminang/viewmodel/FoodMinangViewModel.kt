package com.dicoding.jetpackcompose.foodminang.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
class FoodMinangViewModel(private val repository: FoodRepository):ViewModel() {
    private val _groupFood = MutableStateFlow(
        repository.getFood()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupFood: StateFlow<Map<Char, List<Food>>> get() = _groupFood

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupFood.value = repository.searchFood(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}
class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodMinangViewModel::class.java)) {
            return FoodMinangViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}