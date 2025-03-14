package com.davidsunday.meal_assessment.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseResponse<T>(@SerialName("data") val data: T, val message: String, val status: String)

@Serializable
data class ErrorResponse(
    val status: String,
    val message: String,
    val errors: Map<String, List<String>>? = null
)