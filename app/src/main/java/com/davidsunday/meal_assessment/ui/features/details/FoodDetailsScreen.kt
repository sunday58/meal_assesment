package com.davidsunday.meal_assessment.ui.features.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.data.remote.Food
import com.davidsunday.meal_assessment.ui.components.MenuComponent
import com.davidsunday.meal_assessment.ui.components.TagComponent
import com.davidsunday.meal_assessment.ui.theme.Black1
import com.davidsunday.meal_assessment.ui.theme.Grey5
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme
import com.davidsunday.meal_assessment.ui.theme.White
import com.davidsunday.meal_assessment.util.previewFood

@Composable
fun FoodDetailsScreen(food: Food, onBackClick: () -> Unit) {
    FoodDetailsContent(food = food, onBackClick = { onBackClick() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailsContent(food: Food, onBackClick: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (toolbar, info, createButton) = createRefs()
        TopAppBar(
            title = {},
            navigationIcon = {
                MenuComponent(
                    icon = R.drawable.ic_arrow_square_back,
                    onClick = { onBackClick() },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            actions = {
                MenuComponent(
                    icon = R.drawable.ic_favourite,
                    onClick = { },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
                MenuComponent(
                    icon = R.drawable.ic_edit,
                    onClick = { },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }
        )
        val pagerState = rememberPagerState { food.foodImages.size }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .constrainAs(info) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(toolbar.bottom)
                bottom.linkTo(createButton.top)
                height = Dimension.fillToConstraints
            }) {
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    beyondViewportPageCount = 1,
                    pageContent = { index ->
                        AsyncImage(
                            model = food.foodImages[index].imageUrl,
                            contentDescription = stringResource(R.string.food_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(290.dp)
                                .fillMaxWidth(),
                            placeholder = painterResource(R.drawable.ic_loader),
                            onError = { it.result.throwable.printStackTrace() }
                        )
                    })
                Text(
                    text = "${pagerState.settledPage + 1}/${food.foodImages.count()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black1,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(6.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = food.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(4.dp))
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
            Spacer(Modifier.height(12.dp))
            Text(
                text = food.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp),
                color = Black1
            )
            Spacer(Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = Grey5, shape = RoundedCornerShape(4.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Nutrition", style = MaterialTheme.typography.bodySmall, color = Black1)
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_fire),
                        contentDescription = stringResource(R.string.calories_icon)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${food.calories} Calories",
                        style = MaterialTheme.typography.bodySmall,
                        color = Black1
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FoodDetailsPreview() {
    MeealAppTheme {
        FoodDetailsContent(
            food = previewFood, onBackClick = {}
        )
    }
}
