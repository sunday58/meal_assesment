package com.davidsunday.meal_assessment.ui.features.home

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidsunday.meal_assessment.data.local.HomeAction
import com.davidsunday.meal_assessment.data.local.HomeUiState
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.ui.components.CategoryText
import com.davidsunday.meal_assessment.ui.components.ChipGroup
import com.davidsunday.meal_assessment.ui.components.MealComponent
import com.davidsunday.meal_assessment.ui.components.HomeTopComponent
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme
import com.davidsunday.meal_assessment.ui.theme.White

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFoodClick: (Food) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                is HomeAction.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val pullToRefreshState = remember {
        object : PullToRefreshState {
            private val anim = androidx.compose.animation.core.Animatable(0f, Float.VectorConverter)
            override val distanceFraction: Float
                get() = anim.value

            override val isAnimating: Boolean
                get() = anim.isRunning

            override suspend fun animateToThreshold() {
                anim.animateTo(1F, spring(dampingRatio = Spring.DampingRatioHighBouncy))
            }

            override suspend fun animateToHidden() {
                anim.animateTo(0F)
            }

            override suspend fun snapTo(targetValue: Float) {
                anim.snapTo(targetValue)
            }
        }
    }

    HomeContent(
        modifier = modifier,
        uiState = uiState,
        onCategoryClicked = {
            viewModel.setCategory(it)
        },
        state = pullToRefreshState,
        onRefresh = { viewModel.refresh() },
        onFoodClick = { onFoodClick(it) })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onCategoryClicked: (Category) -> Unit,
    state: PullToRefreshState,
    onRefresh: () -> Unit,
    onFoodClick: (Food) -> Unit
) {
    val config = LocalConfiguration.current

    Column(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(color = White)) {
            if (uiState.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        if (config.screenWidthDp > 600) {
            PullToRefreshBox(
                state = state,
                isRefreshing = uiState.isLoading,
                onRefresh = { onRefresh() }) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item(span = {
                        GridItemSpan(2)
                    }) {
                        HomeTopComponent()
                    }
                    item(span = { GridItemSpan(2) }) {
                        ChipGroup(chipItems = uiState.categories, onItemClick = {
                            onCategoryClicked(it)
                        })
                        Spacer(Modifier.height(8.dp))
                    }
                    item(span = { GridItemSpan(2) }) {
                        if (uiState.foods.isNotEmpty()) {
                            CategoryText(name = uiState.currentCategory?.name.orEmpty())
                        }
                    }
                    items(items = uiState.foods, key = { it.id }) { food ->
                        MealComponent(
                            food = food,
                            modifier = Modifier.animateItem(),
                            onFoodClick = {
                                onFoodClick(food)
                            })
                    }
                }
            }
        } else {
            PullToRefreshBox(
                state = state,
                isRefreshing = uiState.isLoading,
                onRefresh = { onRefresh() }) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        HomeTopComponent()
                    }
                    stickyHeader {
                        ChipGroup(chipItems = uiState.categories, onItemClick = {
                            onCategoryClicked(it)
                        })
                        Spacer(Modifier.height(8.dp))
                    }
                    item {
                        if (uiState.foods.isNotEmpty()) {
                            CategoryText(name = uiState.currentCategory?.name.orEmpty())
                        }
                    }
                    items(items = uiState.foods, key = { it.id }) { food ->
                        MealComponent(
                            food = food,
                            modifier = Modifier.animateItem(),
                            onFoodClick = {
                                onFoodClick(food)
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MeealAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState(
                categories = listOf(
                    Category(
                        id = 0,
                        name = "All",
                        createdAt = "",
                        updatedAt = "",
                        description = ""
                    ),
                    Category(
                        id = 1,
                        name = "Vegetarian",
                        createdAt = "",
                        updatedAt = "",
                        description = ""
                    ),
                    Category(
                        id = 2,
                        name = "Dinner",
                        createdAt = "",
                        updatedAt = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Soup",
                        createdAt = "",
                        updatedAt = "",
                        description = ""
                    )
                )
            ),
            onCategoryClicked = {},
            state = rememberPullToRefreshState(),
            onRefresh = {},
            onFoodClick = {}
        )
    }
}
