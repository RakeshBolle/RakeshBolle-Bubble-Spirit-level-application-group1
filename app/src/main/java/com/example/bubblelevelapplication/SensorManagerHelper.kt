package com.example.bubblelevelapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SensorManagerHelper(context: Context) : SensorEventListener {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val _orientation = MutableStateFlow(Pair(0f, 0f)) // X and Y tilt values
    private val _azimuth = MutableStateFlow(0f) // Azimuth angle
    val orientation: StateFlow<Pair<Float, Float>> get() = _orientation
    val azimuth: StateFlow<Float> get() = _azimuth

    fun startListening() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        magneticField?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = it.values[0]
                    val y = it.values[1]
                    _orientation.value = Pair(x, y)
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    val magneticFieldValues = it.values
                    val rotationMatrix = FloatArray(9)
                    val orientationAngles = FloatArray(3)

                    if (SensorManager.getRotationMatrix(rotationMatrix, null, floatArrayOf(0f, 0f, 0f), magneticFieldValues)) {
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)
                        _azimuth.value = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
