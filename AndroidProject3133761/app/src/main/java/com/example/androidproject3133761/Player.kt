package com.example.androidproject3133761

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlin.math.absoluteValue


class Player : ComponentActivity() {
    private var sound: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedSound = intent.getStringExtra("soundId")
        enableEdgeToEdge()
        sound = createSoundPlayer(selectedSound.toString(), this)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)




        setContent {
            var gyroX by remember { mutableStateOf(0f) }
            var gyroY by remember { mutableStateOf(0f) }
            var gyroZ by remember { mutableStateOf(0f) }

            DisposableEffect(Unit) {
                val listener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        event?.let {
                            gyroX = it.values[0]
                            gyroY = it.values[1]
                            gyroZ = it.values[2]

                            // Update sound properties dynamically
                            updateSoundProperties(gyroX, gyroY, gyroZ)
                        }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }

                sensorManager.registerListener(listener, gyroscope, SensorManager.SENSOR_DELAY_UI)

                onDispose {
                    sensorManager.unregisterListener(listener)
                }
            }




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
                                sound = MediaPlayer.create(this@Player, R.raw.celloc4)
                                sound?.start()
                            }) {
                                Icon(Icons.Filled.PlayArrow, "play")
                            }

                            // IconButton for Pause Action
                            IconButton(onClick = {
                                sound?.pause()
                            }) {
                                Icon(Icons.Filled.Close, "pause")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateSoundProperties(gyroX: Float, gyroY: Float, gyroZ: Float) {
        val volume = calculateVolume(gyroX, gyroY)
        sound?.setVolume(volume, volume)
    }

    private fun calculateVolume(gyroX: Float, gyroY: Float): Float {
        // Map gyroscope values to a volume range (0.0f to 1.0f)
        return (gyroX.absoluteValue + gyroY.absoluteValue).coerceIn(0f, 1f)
    }
}

/**
 * Method that assigns correct sound to media player based on selection in previous activity
 */
fun createSoundPlayer(correctSound: String, context: Context): MediaPlayer? {
    return when (correctSound){
        "strings" -> MediaPlayer.create(context, R.raw.celloc4)
        "synth" -> MediaPlayer.create(context, R.raw.synthbass)
        "brass" -> MediaPlayer.create(context, R.raw.hornc4)
        "wood" -> MediaPlayer.create(context, R.raw.bassoong3)
        else -> {
            MediaPlayer()
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