package com.dicoding.jetpackcompose.foodminang

import com.dicoding.jetpackcompose.foodminang.data.FoodRepository
import java.util.Currency.getInstance

/**
 * Created by Rahmat Hidayat on 21/11/2022.
 */
object Injection {
        fun provideRepository(): FoodRepository {
            return FoodRepository.getInstance()
        }
}