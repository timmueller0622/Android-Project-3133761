package com.example.androidproject3133761

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


class Player : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        SoundPlayer.changeContext(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)


        setContent {
            var gyroX by remember { mutableFloatStateOf(0f) }
            var gyroY by remember { mutableFloatStateOf(0f) }
            var gyroZ by remember { mutableFloatStateOf(0f) }

            DisposableEffect(Unit) {
                val listener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        event?.let {
                            gyroX = it.values[0]
                            gyroY = it.values[1]
                            gyroZ = it.values[2]

                            // Update sound properties dynamically
                            SoundPlayer.updateSoundProperties(gyroX, gyroY, gyroZ)
                        }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }

                // Registers listener when needed
                sensorManager.registerListener(listener, gyroscope, SensorManager.SENSOR_DELAY_UI)

                // Unregister sensor manager to stop listening to sensor events after activity is closed
                onDispose {
                    sensorManager.unregisterListener(listener)
                }
            }

            /**
             * Sets layout using Column and Row for play and pause button
             */
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    GyroscopeDisplay(gyroX, gyroY, gyroZ)
                    Icon(painter = painterResource(id = R.drawable.music), "name")
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                        Row {
                            // IconButton for Start Action
                            IconButton(onClick = {
                                if (SoundPlayer.isPaused())
                                    SoundPlayer.unpause()
                                else
                                    SoundPlayer.playSound()
                            }) {
                                Icon(Icons.Filled.PlayArrow, "play")
                            }

                            // IconButton for Pause Action
                            IconButton(onClick = {
                                if (SoundPlayer.isPlaying())
                                    SoundPlayer.pause()
                            }) {
                                Icon(Icons.Filled.Close, "pause")
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun GyroscopeDisplay(gyroX: Float, gyroY: Float, gyroZ: Float) {
        Text(text = "Gyroscope Data:")
        Text(text = "X: $gyroX")
        Text(text = "Y: $gyroY")
        Text(text = "Z: $gyroZ")
}