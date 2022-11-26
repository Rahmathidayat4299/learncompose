package com.dicoding.jetpackcompose.foodminang.data

import com.dicoding.jetpackcompose.foodminang.model.FoodData
import com.dicoding.jetpackcompose.foodminang.model.FoodList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Rahmat Hidayat on 20/11/2022.
 */
class FoodRepository {
    private val listFood = mutableListOf<FoodList>()
init {
    if (listFood.isEmpty()) {
        FoodData.listFood.forEach {
            listFood.add(FoodList(it, 0))
        }
    }
}
    fun listFood(): Flow<List<FoodList>> {
        return flowOf(listFood)
    }

    fun getlistFoodById(rewardId: Long): FoodList {
        return listFood.first {
            it.food.id == rewardId
        }
    }
    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}