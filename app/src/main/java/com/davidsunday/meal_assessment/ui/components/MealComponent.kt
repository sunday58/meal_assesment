package com.davidsunday.meal_assessment.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.ui.theme.Black1
import com.davidsunday.meal_assessment.ui.theme.Grey1
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme
import com.davidsunday.meal_assessment.ui.theme.White

@Composable
fun MealComponent(food: Food, modifier: Modifier = Modifier, onFoodClick:() -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 1.dp, color = Grey1),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        onClick = { onFoodClick() }) {
        Column {
            AsyncImage(
                model = food.foodImages.firstOrNull()?.imageUrl,
                contentDescription = "Food image",
                modifier = Modifier.height(150.dp),
                placeholder = painterResource(R.drawable.ic_loader),
                contentScale = ContentScale.Crop,
                onError = {
                    it.result.throwable.printStackTrace()
                }
            )

            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1F),
                    color = Black1
                )
                Image(
                    painter = painterResource(R.drawable.ic_favourite),
                    contentDescription = "Favourite icon",
                    modifier = Modifier.wrapContentWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fire),
                    contentDescription = "Calories icon"
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${food.calories} Calories",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black1
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = food.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Black1
            )
            Spacer(Modifier.height(6.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(food.foodTags) {
                    TagComponent(tag = it)
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun FoodComponentPreview() {
    MeealAppTheme {
        MealComponent(
            food = Food(
                category = categories.first(),
                categoryId = 12,
                calories = 120,
                createdAt = "",
                updatedAt = "",
                name = "Garlic Butter Shrimp Pasta",
                description = "Creamy hummus spread on whole grain toast topped with sliced cucumbers and radishes.",
                foodImages = emptyList(),
                foodTags = listOf("healthy", "vegetarian"),
                id = 2
            ),
            onFoodClick = {}
        )
    }
}
