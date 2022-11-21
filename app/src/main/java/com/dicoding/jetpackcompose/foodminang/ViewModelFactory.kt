package com.dicoding.jetpackcompose.foodminang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import com.dicoding.jetpackcompose.foodminang.screen.HomeViewModel
import com.dicoding.jetpackcompose.foodminang.screen.detail.DetailViewModel

/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}