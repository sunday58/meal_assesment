package com.davidsunday.meal_assessment.data.remote


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Food(
    val calories: Int,
    val category: Category,
    @SerialName("category_id")
    val categoryId: Int,
    @SerialName("created_at")
    val createdAt: String,
    val description: String,
    val foodImages: List<FoodImage>,
    val foodTags: List<String>,
    val id: Int,
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String
) : Parcelable

@Parcelize
@Serializable
data class Category(
    @SerialName("created_at")
    val createdAt: String,
    val description: String,
    val id: Int,
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String
) : Parcelable

@Parcelize
@Serializable
data class FoodImage(
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String
) : Parcelable

@Serializable
data class Tag(
    val id: Int,
    val name: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

