package com.example.androidproject3133761

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidproject3133761.ui.theme.AndroidProject3133761Theme

class MainActivity : ComponentActivity() {
    var selectedAudioUri: Uri? by mutableStateOf(null)
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedAudioUri = uri
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = Intent(this, Menu::class.java)
            var customSoundUploaded by remember {mutableStateOf(false)}

            val fileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/mpeg" //filters for only mp3 files
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text("Sound Modulator")
                Spacer(modifier = Modifier.padding(10.dp))
                ExtendedFloatingActionButton(onClick = {startActivity(intent)}) {
                    Icon(Icons.Filled.PlayArrow, "Add")
                    Text("Select Sound")

                }
                Spacer(modifier = Modifier.padding(10.dp))
                ExtendedFloatingActionButton(onClick = {
                    customSoundUploaded = false
                    getContent.launch("audio/*")
                    customSoundUploaded = true
                }) {
                    Icon(Icons.Filled.Add, "Add")
                    if (customSoundUploaded){
                        Text(text = "Selected: " + selectedAudioUri?.path)
                        intent.putExtra("customSound", selectedAudioUri.toString())
                    }
                    else
                        Text("New Custom Sound")

                }

                Spacer(modifier = Modifier.padding(10.dp))
                ExtendedFloatingActionButton(onClick = {}) {
                    Icon(Icons.Filled.Info, "Add")
                    Text("Instructions")
                }
            }
        }
    }
}
