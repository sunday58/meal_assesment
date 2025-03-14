package com.davidsunday.meal_assessment.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MealApi {

    @GET("api/foods")
    suspend fun getAllFoods(): BaseResponse<List<Food>>

    @GET("api/categories")
    suspend fun getAllCategories(): BaseResponse<List<Category>>

    @GET("api/tags")
    suspend fun getAllTags(): BaseResponse<List<Tag>>

    @Multipart
    @POST("api/foods")
    suspend fun createMeals(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>
    ): BaseResponse<Food>
}
