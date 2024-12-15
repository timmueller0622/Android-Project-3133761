package com.example.androidproject3133761

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.mutableStateOf

public class SoundPlayer (private var context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    /**
     * Sound name variable that will get set and passed through intent to play back correct sound in Player class
     */
    private var soundName = ""
    fun setSoundName(newSoundName: String){
        soundName = newSoundName
    }

    fun getSoundName(): String {
        return soundName
    }

    /**
     * Resets the MediaPlayer object.
     */
    fun reset() {
        mediaPlayer?.reset()
        mediaPlayer?.release() // Release the resources before resetting
        mediaPlayer = null
    }

    /**
     * Plays a sound resource.
     *
     * @param resId The resource ID of the sound file to play.
     */
    fun playSound(resId: Int) {
        reset() // Ensure MediaPlayer is reset before creating a new instance
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.start()
    }

    fun playSoundFromUri(resUri: Uri){
        mediaPlayer = MediaPlayer() // Ensure MediaPlayer is initialized with empty MediaPlayer before data source from Uri is set
        mediaPlayer?.apply{
            setDataSource(context, resUri)
            prepare()
            start()
        }
    }


    /**
     * Check if the MediaPlayer is currently playing a sound.
     *
     * @return true if MediaPlayer is playing, false otherwise.
     */
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun changeContext(newContext: Context) {
        context = newContext
    }
}