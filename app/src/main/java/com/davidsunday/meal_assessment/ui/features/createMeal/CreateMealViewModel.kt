package com.davidsunday.meal_assessment.ui.features.createMeal

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidsunday.meal_assessment.data.local.CreateFood
import com.davidsunday.meal_assessment.data.local.CreateMealAction
import com.davidsunday.meal_assessment.data.local.CreateMealUiState
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Tag
import com.davidsunday.meal_assessment.data.repository.MealRepository
import com.davidsunday.meal_assessment.util.handleThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMealViewModel @Inject constructor(private val repository: MealRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<CreateMealUiState> =
        MutableStateFlow(CreateMealUiState())
    val uiState: StateFlow<CreateMealUiState> = _uiState.asStateFlow()

    private val _action: MutableSharedFlow<CreateMealAction> =
        MutableSharedFlow(onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1)
    val action: SharedFlow<CreateMealAction> = _action.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update { it.copy(isLoading = false) }
        _action.tryEmit(CreateMealAction.ShowMessage(throwable.handleThrowable()))
    }

    val buttonState: StateFlow<Boolean> = uiState.map {
        it.calories.isNotBlank() && it.description.isNotBlank() && (it.name.isNotBlank() && it.name.length <= 30) && it.category != null
                && it.selectedTags.isNotEmpty() && it.imageUris.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        getCategories()
        getTags()
    }

    fun setName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun setDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun setCategory(category: Category) {
        _uiState.update { it.copy(category = category) }
    }

    fun setCalories(calories: String) {
        _uiState.update { it.copy(calories = calories) }
    }

    fun setTag(tag: Tag) {
        val currentTags: SnapshotStateList<Tag> = SnapshotStateList()
        currentTags.addAll(uiState.value.selectedTags)
        if (!currentTags.contains(tag)) {
            currentTags.add(tag)
            _uiState.update {
                it.copy(selectedTags = currentTags)
            }
        }
    }

    fun deleteTag(tag: Tag) {
        val currentTags: SnapshotStateList<Tag> = SnapshotStateList()
        currentTags.addAll(uiState.value.selectedTags)
        _uiState.update {
            currentTags.remove(tag)
            it.copy(selectedTags = currentTags)
        }
    }

    fun onAddMealClick() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            repository.createMeals(
                CreateFood(
                    name = uiState.value.name,
                    description = uiState.value.description,
                    categoryId = uiState.value.category?.id ?: 0,
                    calories = uiState.value.calories.toInt(),
                    tags = uiState.value.selectedTags.map { it.id.toString() },
                    images = uiState.value.imageUris
                )
            )
                .onSuccess {
                    _action.tryEmit(CreateMealAction.Success(it.message))
                }
                .onFailure {
                    _action.tryEmit(CreateMealAction.ShowMessage(it.handleThrowable()))
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun setImageUris(uris: List<Uri>) {
        val currentUris: SnapshotStateList<Uri> = SnapshotStateList()
        currentUris.addAll(uiState.value.imageUris)
        val urisToAdd = uris.filter { it !in currentUris }
        currentUris.addAll(urisToAdd)
        _uiState.update {
            it.copy(imageUris = currentUris)
        }
    }

    fun deleteUri(uri: Uri) {
        val currentUris: SnapshotStateList<Uri> = SnapshotStateList()
        currentUris.addAll(uiState.value.imageUris)
        _uiState.update {
            currentUris.remove(uri)
            it.copy(imageUris = currentUris)
        }
    }

    private fun getCategories() {
        viewModelScope.launch(exceptionHandler) {
            repository.getAllCategories()
                .onSuccess { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
                .onFailure {
                    _action.tryEmit(CreateMealAction.ShowMessage(it.handleThrowable()))
                }
        }
    }

    private fun getTags() {
        viewModelScope.launch(exceptionHandler) {
            repository.getAllTags()
                .onSuccess { tags ->
                    _uiState.update { it.copy(tags = tags) }
                }
                .onFailure {
                    _action.tryEmit(CreateMealAction.ShowMessage(it.handleThrowable()))
                }
        }
    }
}

