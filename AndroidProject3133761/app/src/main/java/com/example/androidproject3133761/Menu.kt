package com.example.androidproject3133761

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val soundPlayer = SoundPlayer(this)

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
                    SoundButton(
                        painterResource(id = R.drawable.violin),
                        "strings",
                        soundPlayer,
                        R.raw.celloc4,
                        Uri.EMPTY
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(
                        painterResource(id = R.drawable.keyboard),
                        "synth",
                        soundPlayer,
                        R.raw.synthbass,
                        Uri.EMPTY
                    )
                }
                Row(modifier = Modifier.padding(20.dp)){
                    SoundButton(
                        painterResource(id = R.drawable.trumpet),
                        "brass",
                        soundPlayer,
                        R.raw.hornc4,
                        Uri.EMPTY
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(
                        painterResource(id = R.drawable.recorder),
                        "wood",
                        soundPlayer,
                        R.raw.bassoong3,
                        Uri.EMPTY
                    )
                }

                Row(modifier = Modifier.padding(20.dp)){
                    Spacer(modifier = Modifier.width(20.dp))
                    SoundButton(painterResource(id = R.drawable.music), selectedAudioUri?.path.toString(), soundPlayer, 0, selectedAudioUri)
                }


                ExtendedFloatingActionButton(onClick = {
                    currentIntent.putExtra("soundId", soundPlayer.getSoundName())
                    startActivity(currentIntent)
                }) {
                    Icon(Icons.Filled.PlayArrow, "play")
                    Text("Get Started")
                }
            }
        }
    }
}


/**
 * Creates the sound selector button
 */
@Composable
fun SoundButton(
    icon: Painter,
    name: String,
    soundPlayer: SoundPlayer,
    soundId: Int,
    selectedAudioUri: Uri
){
    soundPlayer.setSoundName(name)
    Button(modifier = Modifier.size(100.dp, 100.dp), shape = RoundedCornerShape(20),onClick = {
        //check for soundId. If 0, then we use alternate way for soundPlayer to play sound via Uri
        if (soundId == 0){
            soundPlayer.playSoundFromUri(selectedAudioUri)
        }
        else
            soundPlayer.playSound(soundId)
    }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Icon(icon, name, modifier = Modifier.size(60.dp))
            Text(name)
        }
    }
}


