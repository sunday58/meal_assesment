package com.davidsunday.meal_assessment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.app_navigation.MealCustomNavType
import com.davidsunday.meal_assessment.app_navigation.MealAppBottomNav
import com.davidsunday.meal_assessment.app_navigation.BottomNavDestinations
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.ui.features.add.AddScreen
import com.davidsunday.meal_assessment.ui.features.createMeal.CreateMealScreen
import com.davidsunday.meal_assessment.ui.features.details.FoodDetailsScreen
import com.davidsunday.meal_assessment.ui.features.favourite.FavouriteScreen
import com.davidsunday.meal_assessment.ui.features.generator.GeneratorScreen
import com.davidsunday.meal_assessment.ui.features.home.HomeScreen
import com.davidsunday.meal_assessment.ui.features.planner.PlannerScreen
import com.davidsunday.meal_assessment.ui.theme.Black1
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MeealAppTheme {
                val currentBackStack by navController.currentBackStackEntryAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentBackStack?.destination?.hierarchy?.any {
                                it.hasRoute(BottomNavDestinations.Home::class) || it.hasRoute(
                                    BottomNavDestinations.Generator::class
                                ) || it.hasRoute(
                                    BottomNavDestinations.Add::class
                                ) || it.hasRoute(
                                    BottomNavDestinations.Favourite::class
                                ) || it.hasRoute(
                                    BottomNavDestinations.Planner::class
                                )
                            } == true) {
                            MealAppBottomNav(navController = navController)
                        }
                    },
                    floatingActionButton = {
                        if (currentBackStack?.destination?.hierarchy?.any {
                                it.hasRoute(BottomNavDestinations.Home::class)
                            } == true) {
                            ExtendedFloatingActionButton(onClick = {
                                navController.navigate(BottomNavDestinations.CreateFood)
                            }) {
                                Image(
                                    painter = painterResource(R.drawable.ic_add_food),
                                    contentDescription = stringResource(R.string.add_food_icon)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.add_food),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Black1
                                )
                            }
                        }
                    }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavDestinations.Home
                    ) {
                        composable<BottomNavDestinations.Home> {
                            HomeScreen(
                                modifier = Modifier.padding(paddingValues),
                                onFoodClick = { food ->
                                    navController.navigate(route = BottomNavDestinations.FoodDetails(food))
                                })
                        }
                        composable<BottomNavDestinations.Generator> {
                            GeneratorScreen(modifier = Modifier.padding(paddingValues))
                        }
                        composable<BottomNavDestinations.Add> {
                            AddScreen(modifier = Modifier.padding(paddingValues))
                        }
                        composable<BottomNavDestinations.Favourite> {
                            FavouriteScreen(modifier = Modifier.padding(paddingValues))
                        }
                        composable<BottomNavDestinations.Planner> {
                            PlannerScreen(modifier = Modifier.padding(paddingValues))
                        }
                        composable<BottomNavDestinations.CreateFood> {
                            CreateMealScreen(onBackPress = { navController.navigateUp() })
                        }
                        composable<BottomNavDestinations.FoodDetails>(typeMap = mapOf(typeOf<Food>() to MealCustomNavType.foodType)) {
                            val arguments = it.toRoute<BottomNavDestinations.FoodDetails>()
                            FoodDetailsScreen(food = arguments.food, onBackClick = { navController.navigateUp() })
                        }
                    }
                }
            }
        }
    }
}
