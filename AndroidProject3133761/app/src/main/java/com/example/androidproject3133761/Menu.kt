package com.example.androidproject3133761

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class Menu : ComponentActivity() {
    private var soundId = mutableStateOf("")
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var playSound: MediaPlayer = MediaPlayer() //initialization happens here with an empty player. sounds added for every button
            val context = this //context relevant to create mediaplayer and pass along in intent

            //add null check for uri
            var selectedAudioUri = Uri.EMPTY
            val uriFromIntent = intent.getStringExtra("customSound")
            if (uriFromIntent == null){

            }
            else
                selectedAudioUri = Uri.parse(uriFromIntent)

            val currentIntent = Intent(this, Player::class.java)

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Text("Choose Your Sound")
                Row(modifier = Modifier.padding(10.dp)){
                    SoundButton(painterResource(id = R.drawable.violin), "strings", {
                        playSound.reset()
                        //media player needs to be reinitialized every time it is reset so a differet sound can be attached
                        playSound = MediaPlayer.create(context, R.raw.celloc4)
                        playSound.start()
                        soundId.value = "string"
                    })
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(painterResource(id = R.drawable.keyboard), "synth", {
                        playSound.reset()
                        playSound = MediaPlayer.create(context, R.raw.synthbass)
                        playSound.start()
                        soundId.value = "synth"
                    })
                }
                Row(modifier = Modifier.padding(20.dp)){
                    SoundButton(painterResource(id = R.drawable.trumpet), "brass", {
                        playSound.reset()
                        playSound = MediaPlayer.create(context, R.raw.hornc4)
                        playSound.start()
                        soundId.value = "brass"
                    })
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(painterResource(id = R.drawable.recorder), "wood", {
                        playSound.reset()
                        playSound = MediaPlayer.create(context, R.raw.bassoong3)
                        playSound.start()
                        soundId.value = "wood"
                    })
                }
                Row(modifier = Modifier.padding(20.dp)){
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(painterResource(id = R.drawable.music), selectedAudioUri?.path.toString(), {
                        playSound = MediaPlayer().apply {
                            //setDataSource(this@Menu, selectedAudioUri!!)
                            prepare()
                            start()
                        }

                        soundId.value = selectedAudioUri?.path.toString()
                    })
                }
                //SoundButton(painterResource(id = R.drawable.music), "custom", {})
                ExtendedFloatingActionButton(onClick = {
                    currentIntent.putExtra("soundId", soundId.value)
                    startActivity(currentIntent)
                }) {
                    Icon(Icons.Filled.PlayArrow, "play")
                    Text("Get Started")
                }
            }

        }
    }
}
//Creates the sound selection button
@Composable
fun SoundButton(icon: Painter, name: String, onClick: () -> Unit){
    Button(modifier = Modifier.size(100.dp, 100.dp), shape = RoundedCornerShape(20),onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Icon(icon, name, modifier = Modifier.size(60.dp))
            Text(name)
        }
    }
}
