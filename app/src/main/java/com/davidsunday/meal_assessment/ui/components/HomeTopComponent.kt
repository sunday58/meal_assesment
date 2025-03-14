package com.davidsunday.meal_assessment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.ui.theme.Black1
import com.davidsunday.meal_assessment.ui.theme.Grey3
import com.davidsunday.meal_assessment.ui.theme.Grey4
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme

@Composable
fun HomeTopComponent() {
    Column {
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.ic_user_avatar),
                contentDescription = stringResource(R.string.profile_photo),
                modifier = Modifier
                    .size(42.dp)
                    .align(Alignment.CenterStart)
            )
            MenuComponent(
                icon = R.drawable.ic_notification,
                onClick = {},
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.hey_there_lucy),
            style = MaterialTheme.typography.titleMedium,
            color = Black1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.are_you_excited_to_create_a_tasty_dish_today),
            style = MaterialTheme.typography.bodySmall,
            color = Grey3,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            value = "",
            onValueChange = {},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = stringResource(R.string.search_foods),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_icon)
                )
            },
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Grey4,
                unfocusedContainerColor = Grey4
            )
        )
    }
}

@Composable
fun CategoryText(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        color = Black1,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview
@Composable
fun HomeTopComponentPreview() {
    MeealAppTheme {
        HomeTopComponent()
    }
}
