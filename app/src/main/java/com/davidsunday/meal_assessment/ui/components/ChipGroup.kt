package com.davidsunday.meal_assessment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assessment.data.remote.Category
import com.davidsunday.meal_assessment.ui.theme.Blue
import com.davidsunday.meal_assessment.ui.theme.Grey1
import com.davidsunday.meal_assessment.ui.theme.Grey2
import com.davidsunday.meal_assessment.ui.theme.White

@Composable
fun ChipGroup(chipItems: List<Category>, onItemClick: (Category) -> Unit) {

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp)
    ) {
        itemsIndexed(chipItems) { index, chipItem ->
            val isSelected = selectedIndex == index
            FilterChip(
                selected = true,
                onClick = {
                    selectedIndex = index
                    onItemClick(chipItem)
                },
                label = {
                    Text(
                        text = chipItem.name,
                        modifier = Modifier.padding(10.dp),
                        color = if (isSelected) White else Grey2,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(4.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (isSelected) Blue else Grey1
                )
            )
        }
    }
}

val categories = listOf(
    Category("", "", 1, "All", ""),
    Category("", "", 1, "Morning Feast", ""),
    Category("", "", 1, "Sunrise Meal", ""),
    Category("", "", 1, "Dawn Delicacies", "")
)

@Preview
@Composable
fun ChipGroupPreview() {
    ChipGroup(chipItems = categories) {

    }
}
