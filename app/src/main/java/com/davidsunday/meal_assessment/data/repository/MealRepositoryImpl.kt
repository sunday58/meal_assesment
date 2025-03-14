package com.davidsunday.meal_assessment.data.repository

import android.content.Context
import com.davidsunday.meal_assessment.di.IODispatcher
import com.davidsunday.meal_assessment.data.local.db.AppDatabase
import com.davidsunday.meal_assessment.data.remote.BaseResponse
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.data.remote.MealApi
import com.davidsunday.meal_assessment.data.remote.Tag
import com.davidsunday.meal_assessment.data.local.CreateFood
import com.davidsunday.meal_assessment.util.getMediaTypeForFile
import com.davidsunday.meal_assessment.util.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val api: MealApi,
    private val database: AppDatabase,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : MealRepository {

    override suspend fun getMeals(): Result<List<Food>> {
        return try {
            val response = withContext(dispatcher) {
                api.getAllFoods().data.sortedByDescending { it.createdAt }
            }
            Result.success(response)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getAllCategories(): Result<List<Category>> {
        var categories: List<Category> = emptyList()
        return try {
            categories = database.getCategories().ifEmpty {
                withContext(dispatcher) { api.getAllCategories().data }
            }
            Result.success(categories)
        } catch (t: Throwable) {
            Result.failure(t)
        } finally {
            database.setCategories(categories)
        }
    }

    override suspend fun createMeals(meal: CreateFood): Result<BaseResponse<Food>> {
        return try {
            withContext(dispatcher) {
                val name = meal.name.toRequestBody("text/plain".toMediaType())
                val description = meal.description.toRequestBody("text/plain".toMediaType())
                val categoryId =
                    meal.categoryId.toString().toRequestBody("text/plain".toMediaType())
                val calories = meal.calories.toString().toRequestBody("text/plain".toMediaType())
                val tags = meal.tags.mapIndexed { index, tag ->
                    val tagRequestBody = tag.toRequestBody("text/plain".toMediaType())
                    MultipartBody.Part.createFormData("tags[$index]", null, tagRequestBody)
                }

                val images = meal.images.mapIndexed { index, uri ->
                    val imageFile = uriToFile(context, uri)
                    MultipartBody.Part.createFormData(
                        name = "images[$index]",
                        filename = "${imageFile.name}_$index",
                        body = imageFile.asRequestBody(getMediaTypeForFile(imageFile))
                    )
                }
                val response = api.createMeals(name, description, categoryId, calories, tags, images)
                Result.success(response)
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getAllTags(): Result<List<Tag>> {
        var tags: List<Tag> = emptyList()
        return try {
            tags = database.getTags().ifEmpty {
                withContext(dispatcher) {
                    api.getAllTags().data
                }
            }
            Result.success(tags)
        } catch (t: Throwable) {
            Result.failure(t)
        } finally {
            database.setTags(tags)
        }
    }
}
