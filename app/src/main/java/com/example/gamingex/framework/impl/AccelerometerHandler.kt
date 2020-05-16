package com.example.gamingex.framework.impl

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerHandler(context: Context): SensorEventListener {

    init {
        val manager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size != 0) {
            val accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER)[0]
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    var accelX: Float = 0f
    var accelY: Float = 0f
    var accelZ: Float = 0f

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int): Unit = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        accelX = event?.values?.get(0) ?: 0f
        accelY = event?.values?.get(1) ?: 0f
        accelZ = event?.values?.get(2) ?: 0f
    }
}
