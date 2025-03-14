package com.davidsunday.meal_assessment.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.ui.theme.Grey1
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme

@Composable
fun MenuComponent(@DrawableRes icon: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .border(width = 1.dp, color = Grey1, shape = CircleShape)
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuComponentPreview() {
    MeealAppTheme {
        MenuComponent(icon = R.drawable.ic_notification, onClick = {})
    }
}