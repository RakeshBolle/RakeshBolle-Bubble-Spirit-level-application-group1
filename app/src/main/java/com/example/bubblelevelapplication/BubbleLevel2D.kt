package com.example.bubblelevelapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun OrientationBoxWithNorth(xTilt: Float, yTilt: Float, azimuth: Float) {
    val range = 10f  // Working range [-10, 10] degrees for both X and Y
    val animatedX = xTilt.coerceIn(-range, range)
    val animatedY = yTilt.coerceIn(-range, range)

    Canvas(
        modifier = Modifier
            .size(250.dp)
            .padding(16.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2

        // Draw outer circle
        drawCircle(
            color = Color.Gray,
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 5f)
        )

        // Draw North line
        val northX = centerX + radius * cos(Math.toRadians(azimuth.toDouble())).toFloat()
        val northY = centerY - radius * sin(Math.toRadians(azimuth.toDouble())).toFloat()
        drawLine(
            color = Color.Yellow,
            start = Offset(centerX, centerY),
            end = Offset(northX, northY),
            strokeWidth = 5f
        )

        // Calculate bubble position
        val bubbleX = centerX + (animatedX / range) * (radius - 25f)
        val bubbleY = centerY - (animatedY / range) * (radius - 25f)

        // Draw bubble
        drawCircle(
            color = if (animatedX.absoluteValue > 8 || animatedY.absoluteValue > 8) Color.Red else Color.Blue,
            radius = 25f,
            center = Offset(bubbleX, bubbleY)
        )
    }
}
