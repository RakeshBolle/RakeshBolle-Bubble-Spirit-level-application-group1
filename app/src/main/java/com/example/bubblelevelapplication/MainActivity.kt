package com.example.bubblelevelapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private lateinit var sensorHelper: SensorManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SensorManagerHelper
        sensorHelper = SensorManagerHelper(this)
        sensorHelper.startListening()

        setContent {
            BubbleLevelApp(sensorHelper)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.stopListening()
    }
}

@Composable
fun BubbleLevelApp(sensorHelper: SensorManagerHelper) {
    val orientation by sensorHelper.orientation.collectAsState(Pair(0f, 0f))
    val xTilt = orientation.first
    val yTilt = orientation.second
    val azimuth by sensorHelper.azimuth.collectAsState(0f)

    // States for max and min values of X and Y tilt
    var maxX by remember { mutableStateOf(xTilt) }
    var minX by remember { mutableStateOf(xTilt) }
    var maxY by remember { mutableStateOf(yTilt) }
    var minY by remember { mutableStateOf(yTilt) }

    // Update max and min values
    maxX = maxOf(maxX, xTilt)
    minX = minOf(minX, xTilt)
    maxY = maxOf(maxY, yTilt)
    minY = minOf(minY, yTilt)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E88E5))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Responsive Device Orientation",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
            )

            // 2D Orientation Box with North Indicator
            OrientationBoxWithNorth(xTilt, yTilt, azimuth)

            // Angle Display with max and min values
            AngleDisplay(xTilt, yTilt, maxX, minX, maxY, minY)

            // Orientation Status
            OrientationStatus(xTilt, yTilt)
        }
    }
}
