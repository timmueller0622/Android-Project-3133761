package com.example.androidproject3133761

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.IOException

@SuppressLint("StaticFieldLeak")
object SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var context: Context

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

    fun init(currentContext: Context) {
        context = currentContext.applicationContext
    }

    /**
     * Resets the MediaPlayer object.
     */
    private fun reset() {
        mediaPlayer?.stop()
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

    fun playSoundFromUri( resUri: Uri){

        try {
            reset() // Ensure previous MediaPlayer is released
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, resUri)
                prepare() // Prepares synchronously; consider prepareAsync if needed
                start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("SoundPlayer", "Error playing sound from URI: ${e.message}")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e("SoundPlayer", "MediaPlayer is in an invalid state: ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("SoundPlayer", "Unexpected error: ${e.message}")
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