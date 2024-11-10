package com.example.androidproject3133761

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidproject3133761.ui.theme.AndroidProject3133761Theme

var soundId = mutableStateOf("")
class Menu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = Intent(this, Player::class.java)
            var playSound: MediaPlayer = MediaPlayer()
            val context = this
            //val mp2 = arrayOf(MediaPlayer.create(this, R.raw.celloc4), MediaPlayer.create(this, R.raw.synthbass), MediaPlayer.create(this, R.raw.hornc4), MediaPlayer.create(this, R.raw.bassoong3))
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Text("Choose Your Sound")
                Row(modifier = Modifier.padding(10.dp)){
                    SoundButton(painterResource(id = R.drawable.violin), "strings", {
                        playSound.reset()
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
                // SoundButton(painterResource(id = R.drawable.music), "custom", {})
                ExtendedFloatingActionButton(onClick = {
                    intent.putExtra("soundId", soundId.value)
                    startActivity(intent)
                }) {
                    Icon(Icons.Filled.PlayArrow, "play")
                    Text("Get Started")
                }
            }

        }
    }
}

@Composable
fun SoundButton(icon: Painter, name: String, onClick: () -> Unit){
    Button(modifier = Modifier.size(100.dp, 100.dp), shape = RoundedCornerShape(20),onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Icon(icon, name, modifier = Modifier.size(60.dp))
            Text(name)
        }

    }
}

fun startStop(allSounds: Array<MediaPlayer>, soundToPlay: Int){
    var c = 0
    for (sound in allSounds){
        if (c == soundToPlay)
            continue;
        sound.reset()
        c++
    }
    allSounds[soundToPlay].start()
}
