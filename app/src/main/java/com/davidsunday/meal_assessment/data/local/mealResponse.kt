package com.davidsunday.meal_assessment.data.local

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.data.remote.Tag

/**
 * davidsunday
 */

data class CreateMealUiState(
    val name: String = "",
    val description: String = "",
    val category: Category? = null,
    val calories: String = "",
    val selectedTags: SnapshotStateList<Tag> = SnapshotStateList(),
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val imageUris: SnapshotStateList<Uri> = SnapshotStateList()
)

sealed class CreateMealAction {
    data class ShowMessage(val message: String?) : CreateMealAction()
    data class Success(val message: String?) : CreateMealAction()
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val foods: SnapshotStateList<Food> = SnapshotStateList(),
    val currentCategory: Category? = null
)

sealed class HomeAction {
    data class ShowMessage(val message: String?) : HomeAction()
}
