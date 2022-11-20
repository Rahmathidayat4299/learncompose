package com.dicoding.jetpackcompose.foodminang.data

import com.dicoding.jetpackcompose.foodminang.model.Food
import com.dicoding.jetpackcompose.foodminang.model.FoodData

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
class FoodRepository {
    fun getFood(): List<Food> = FoodData.listFood

    fun searchFood(query: String): List<Food>{
        return FoodData.listFood.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}