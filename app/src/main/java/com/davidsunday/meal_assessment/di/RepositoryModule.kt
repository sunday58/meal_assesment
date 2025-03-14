package com.davidsunday.meal_assessment.di

import com.davidsunday.meal_assessment.data.repository.MealRepository
import com.davidsunday.meal_assessment.data.repository.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRepository(foodRepositoryImpl: MealRepositoryImpl): MealRepository
}
