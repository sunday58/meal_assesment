package com.davidsunday.meal_assessment.data.local

import android.net.Uri

data class CreateFood(
    val name: String,
    val description: String,
    val categoryId: Int,
    val calories: Int,
    val tags: List<String>,
    val images: List<Uri>
)
