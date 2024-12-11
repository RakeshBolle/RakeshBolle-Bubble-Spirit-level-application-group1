package com.example.bubblelevelapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun OrientationStatus(xTilt: Float, yTilt: Float) {
    val isFlat = xTilt.absoluteValue < 0.5f && yTilt.absoluteValue < 0.5f

    Text(
        text = if (isFlat) "Device is Flat" else "Device is Tilted",
        color = if (isFlat) Color.Green else Color.Red,
        style = TextStyle(fontSize = 20.sp)
    )
}
