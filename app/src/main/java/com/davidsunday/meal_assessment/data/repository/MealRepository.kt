package com.davidsunday.meal_assessment.data.repository

import com.davidsunday.meal_assessment.data.remote.BaseResponse
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.data.remote.Tag
import com.davidsunday.meal_assessment.data.local.CreateFood

interface MealRepository {

    suspend fun getMeals(): Result<List<Food>>

    suspend fun getAllCategories(): Result<List<Category>>

    suspend fun createMeals(food: CreateFood): Result<BaseResponse<Food>>

    suspend fun getAllTags(): Result<List<Tag>>
}
