package com.davidsunday.meal_assessment.ui.features.favourite

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assesment.R

@Composable
fun FavouriteScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.favourite_screen),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(24.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
