package com.davidsunday.meal_assessment.util

import android.content.Context
import android.net.Uri
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.ErrorResponse
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.data.remote.FoodImage
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.HttpException
import java.io.File
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getMediaTypeForFile(file: File): MediaType? {
    return when (file.extension.lowercase()) {
        "png" -> "image/png".toMediaTypeOrNull()
        "jpg", "jpeg" -> "image/jpeg".toMediaTypeOrNull()
        "webp" -> "image/webp".toMediaTypeOrNull()
        else -> "application/octet-stream".toMediaTypeOrNull()
    }
}

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image")
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file
}

fun createImageFile(context: Context): File {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(null)
    return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
}

fun Throwable.handleThrowable(): String {
    return when (this) {
        is UnknownHostException -> "Check network connection"
        is SocketTimeoutException -> "Your network is slow. Try again with a better network"
        is HttpException -> {
            if (this.code() in 500..599) {
                "It's not you, it's us. Please try again later"
            } else if (this.code() in 400..499) {
                val errorMessage = StringBuilder()
                this.response()?.run {
                    this.errorBody()?.let { responseBody ->
                        val errorString = responseBody.string()
                        val errorBody = Json.decodeFromString<ErrorResponse>(errorString)
                        if (errorBody.errors != null) {
                            errorBody.errors.forEach { (key, value) ->
                                errorMessage.append(
                                    "${key.uppercase(Locale.getDefault())}: ".plus(
                                        value.firstOrNull().plus("\n")
                                    )
                                )
                            }
                        }
                    }
                }
                errorMessage.toString()
            } else {
                "Something went wrong"
            }
        }

        else -> this.localizedMessage ?: "Something went wrong"
    }
}

val previewFood = Food(
    calories = 1,
    categoryId = 1,
    category = Category(
        createdAt = "",
        updatedAt = "",
        name = "Healthy",
        description = "",
        id = 1
    ),
    updatedAt = "",
    createdAt = "",
    description = "Creamy hummus spread on whole grain toast topped with sliced cucumbers and radishes, creating a delightful blend of textures and flavors. The smoothness of the hummus complements the crunchiness of the cucumbers, while the radishes add a peppery bite that elevates the dish. To enhance this already delicious combination, consider adding a sprinkle of paprika for a touch of warmth, or a drizzle of olive oil to enrich the flavors further. You could also incorporate some fresh herbs, like dill or parsley, to introduce a burst of freshness. ",
    name = "Garlic Butter Shrimp Pasta",
    id = 1,
    foodTags = listOf("Vegetarian", "Healthy"),
    foodImages = listOf(
        FoodImage(id = 1, imageUrl = ""),
        FoodImage(id = 2, imageUrl = ""),
        FoodImage(id = 3, imageUrl = ""),
    )
)
