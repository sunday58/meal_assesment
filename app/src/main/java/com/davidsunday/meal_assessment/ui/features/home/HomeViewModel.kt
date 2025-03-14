package com.davidsunday.meal_assessment.ui.features.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidsunday.meal_assessment.data.local.HomeAction
import com.davidsunday.meal_assessment.data.local.HomeUiState
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.data.repository.MealRepository
import com.davidsunday.meal_assessment.util.handleThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MealRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _action: MutableSharedFlow<HomeAction> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val action: SharedFlow<HomeAction> = _action.asSharedFlow()
    private var originalFoodList = SnapshotStateList<Food>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update { it.copy(isLoading = false) }
        _action.tryEmit(HomeAction.ShowMessage(throwable.handleThrowable()))
    }

    init {
        getCategories()
        getFoods()
    }

    private fun getCategories() = viewModelScope.launch(exceptionHandler) {
        repository.getAllCategories()
            .onSuccess { categories ->
                val updatedCategories = categories.toMutableList()
                updatedCategories.add(
                    0,
                    Category(
                        name = "All",
                        id = 0,
                        createdAt = "",
                        updatedAt = "",
                        description = ""
                    )
                )
                _uiState.update {
                    it.copy(
                        categories = updatedCategories,
                        currentCategory = updatedCategories.first()
                    )
                }
            }
            .onFailure {
                _action.tryEmit(HomeAction.ShowMessage(it.handleThrowable()))
            }
    }

    private fun getFoods() = viewModelScope.launch(exceptionHandler) {
        _uiState.update { it.copy(isLoading = true) }
        repository.getMeals()
            .onSuccess { foods ->
                originalFoodList.clear()
                originalFoodList.addAll(foods)
                _uiState.update { it.copy(foods = originalFoodList) }
            }
            .onFailure {
                _action.tryEmit(HomeAction.ShowMessage(it.handleThrowable()))
            }
        _uiState.update { it.copy(isLoading = false) }
    }

    fun setCategory(category: Category) {
        if (category.name.equals("All", ignoreCase = true)) {
            _uiState.update { it.copy(foods = originalFoodList, currentCategory = category) }
        } else {
            val currentFoods = SnapshotStateList<Food>()
            currentFoods.addAll(originalFoodList.filter { food ->
                food.category.name.equals(category.name, ignoreCase = true)
            })
            _uiState.update { it.copy(foods = currentFoods, currentCategory = category) }
        }
    }

    fun refresh() {
        viewModelScope.launch(exceptionHandler) {
            getCategories()
            getFoods().join()
            uiState.value.currentCategory?.let {
                setCategory(it)
            }
        }
    }
}
