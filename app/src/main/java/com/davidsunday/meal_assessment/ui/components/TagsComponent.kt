package com.davidsunday.meal_assessment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.data.remote.Tag
import com.davidsunday.meal_assessment.ui.theme.Grey1
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsComponent(modifier: Modifier = Modifier, tags: List<Tag>, onDelete: (Tag) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        if (tags.isEmpty()) {
            Text(
                text = stringResource(R.string.add_a_tag),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .padding(start = 16.dp)
            )
        } else {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                overflow = FlowRowOverflow.Clip
            ) {
                tags.forEach { tag ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(color = Grey1, shape = RoundedCornerShape(2.dp))
                            .padding(horizontal = 8.dp, vertical = 1.dp)
                            .height(25.dp)
                    ) {
                        Text(
                            text = tag.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(4.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = stringResource(R.string.delete_tag),
                            modifier = Modifier.clickable { onDelete(tag) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsComponentPreview() {
    MeealAppTheme {
        TagsComponent(
            tags = listOf(
                Tag(id = 1, name = "Spicy", createdAt = "", updatedAt = ""),
                Tag(id = 2, name = "Healthy", createdAt = "", updatedAt = ""),
                Tag(id = 3, name = "Vegetarian", createdAt = "", updatedAt = ""),
                Tag(id = 4, name = "Keto", createdAt = "", updatedAt = ""),
                Tag(id = 5, name = "Low carb", createdAt = "", updatedAt = ""),
                Tag(id = 6, name = "Fibre", createdAt = "", updatedAt = ""),
                Tag(id = 7, name = "Creamy", createdAt = "", updatedAt = ""),
            ),
            onDelete = {

            }
        )
    }
}
