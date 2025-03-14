package com.davidsunday.meal_assessment.app_navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.ui.theme.Blue
import com.davidsunday.meal_assessment.ui.theme.DefaultIconColor
import com.davidsunday.meal_assessment.ui.theme.White

@Composable
fun MealAppBottomNav(navController: NavHostController) {
    val navBackStack by navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = White
    ) {
        bottomNavItems.forEach { navItem ->
            val isCurrentItem = (navBackStack?.destination?.hierarchy?.any {
                it.hasRoute(
                    navItem.route::class
                )
            } == true)
            NavigationBarItem(
                selected = isCurrentItem,
                colors = NavigationBarItemDefaults.colors().copy(selectedIndicatorColor = White),
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = if (isCurrentItem) painterResource(navItem.iconSelected) else painterResource(
                            navItem.iconUnselected
                        ),
                        contentDescription = navItem.label,
                        tint = if (isCurrentItem) Blue else DefaultIconColor
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontWeight = if (isCurrentItem) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isCurrentItem) Blue else DefaultIconColor
                    )
                }
            )
        }
    }
}

data class MealBottomNavItem(
    val label: String,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnselected: Int,
    val route: BottomNavDestinations
)

val bottomNavItems = listOf(
    MealBottomNavItem(
        label = "Home",
        iconSelected = R.drawable.ic_home,
        iconUnselected = R.drawable.ic_home,
        route = BottomNavDestinations.Home
    ),
    MealBottomNavItem(
        label = "Generator",
        iconSelected = R.drawable.ic_generator,
        iconUnselected = R.drawable.ic_generator,
        route = BottomNavDestinations.Generator
    ),
    MealBottomNavItem(
        label = "Add",
        iconSelected = R.drawable.ic_add,
        iconUnselected = R.drawable.ic_add,
        route = BottomNavDestinations.Add
    ),
    MealBottomNavItem(
        label = "Favourite",
        iconSelected = R.drawable.ic_favourite,
        iconUnselected = R.drawable.ic_favourite,
        route = BottomNavDestinations.Favourite
    ),
    MealBottomNavItem(
        label = "Planner",
        iconSelected = R.drawable.ic_planner,
        iconUnselected = R.drawable.ic_planner,
        route = BottomNavDestinations.Planner
    )
)

@Preview
@Composable
fun FoodAppBottomNavPreview() {
    MealAppBottomNav(navController = rememberNavController())
}
