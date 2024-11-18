package com.example.androidproject3133761

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class Player : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedSound = intent.getStringExtra("soundId")
        enableEdgeToEdge()
        val sound = SelectedSound(selectedSound.toString(), this)
        setContent {
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
                    Icon(painter = painterResource(id = R.drawable.music), "name")
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                        Row {
                            // IconButton for Start Action
                            IconButton(onClick = {
                                if (sound != null) { //nullcheck necessary
                                    sound.start()
                                }
                            }) {
                                Icon(Icons.Filled.PlayArrow, "play")
                            }

                            // IconButton for Pause Action
                            IconButton(onClick = {
                                if (sound != null) { //nullcheck necessary
                                    sound.pause()
                                }
                            }) {
                                Icon(Icons.Filled.Close, "pause")
                            }
                        }
                    }
                }
                // Buttons content
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Blue.copy(alpha = 0.5f)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    //Controls button that will pop out user controls later on
                    Button(onClick = {}){
                        Text("Controls")
                    }
                }
            }
        }
    }
}

//method that assigns correct sound to media player based on selection in previous activity
fun SelectedSound(correctSound: String, context: Context): MediaPlayer? {
    when (correctSound){
        "string" -> return MediaPlayer.create(context, R.raw.celloc4)
        "synth" -> return MediaPlayer.create(context, R.raw.synthbass)
        "brass" -> return MediaPlayer.create(context, R.raw.hornc4)
        "wood" -> return MediaPlayer.create(context, R.raw.bassoong3)
        else -> {
            return MediaPlayer()
        }
    }
}