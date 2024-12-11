package com.example.bubblelevelapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun AngleDisplay(xTilt: Float, yTilt: Float, maxX: Float, minX: Float, maxY: Float, minY: Float) {
    Text(
        text = "Current Angles: X = %.1f°, Y = %.1f°".format(xTilt, yTilt),
        color = Color.White,
        style = TextStyle(fontSize = 18.sp)
    )
    Text(
        text = "X Tilt: Max = %.1f°, Min = %.1f°".format(maxX, minX),
        color = Color.White,
        style = TextStyle(fontSize = 18.sp)
    )
    Text(
        text = "Y Tilt: Max = %.1f°, Min = %.1f°".format(maxY, minY),
        color = Color.White,
        style = TextStyle(fontSize = 18.sp)
    )
}
