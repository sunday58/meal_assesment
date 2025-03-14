package com.davidsunday.meal_assessment.app_navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.davidsunday.meal_assessment.data.remote.Food
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MealCustomNavType {
    val foodType = object : NavType<Food>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Food? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Food {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Food): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Food) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}
