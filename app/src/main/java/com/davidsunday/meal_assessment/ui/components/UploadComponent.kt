package com.davidsunday.meal_assessment.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidsunday.meal_assesment.R
import com.davidsunday.meal_assessment.ui.theme.Black1
import com.davidsunday.meal_assessment.ui.theme.Grey1
import com.davidsunday.meal_assessment.ui.theme.MeealAppTheme

@Composable
fun UploadComponent(
    @DrawableRes image: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Grey1,
                shape = RoundedCornerShape(4.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Image(painter = painterResource(image), contentDescription = null)
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = Black1
        )
        Spacer(Modifier.height(12.dp))
    }
}

@Preview
@Composable
fun UploadComponentPreview() {
    MeealAppTheme {
        UploadComponent(image = R.drawable.ic_camera, text = "Take Photo", onClick = {})
    }
}
