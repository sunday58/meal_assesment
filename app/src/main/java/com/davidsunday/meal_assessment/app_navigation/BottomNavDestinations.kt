package com.davidsunday.meal_assessment.app_navigation

import com.davidsunday.meal_assessment.data.remote.Food
import kotlinx.serialization.Serializable

sealed class BottomNavDestinations {
    @Serializable
    data object Home : BottomNavDestinations()

    @Serializable
    data object Generator : BottomNavDestinations()

    @Serializable
    data object Add : BottomNavDestinations()

    @Serializable
    data object Favourite : BottomNavDestinations()

    @Serializable
    data object Planner : BottomNavDestinations()

    @Serializable
    data object CreateFood : BottomNavDestinations()

    @Serializable
    data class FoodDetails(val food: Food) : BottomNavDestinations()
}
