package com.davidsunday.meal_assessment.ui.features.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme

@Composable
fun AddScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.add_screen),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(24.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}


@Composable
@Preview(showSystemUi = true)
fun AddScreenPreview() {
    MeealAppTheme {
        Surface {
            AddScreen()
        }
    }
}
