package com.example.androidproject3133761

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.IOException
import kotlin.math.absoluteValue

@SuppressLint("StaticFieldLeak")
object SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var lastRes: Int = 0
    private var lastUri = Uri.EMPTY
    private var paused = false
    private lateinit var context: Context

    /**
     * Sound name variable that will get set and passed through intent to play back correct sound in Player class
     */
    private var soundName = ""
    fun setSoundName(newSoundName: String){
        soundName = newSoundName
    }

    fun init(currentContext: Context) {
        context = currentContext.applicationContext
    }

    /**
     * Resets the MediaPlayer object.
     */
    fun reset() {
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
    fun playNewSound(resId: Int) {
        reset() // Ensure MediaPlayer is reset before creating a new instance
        mediaPlayer = MediaPlayer.create(context, resId)
        lastRes = resId
        lastUri = Uri.EMPTY //Uri is set to empty to allow playSound to null check the Uri properly
        mediaPlayer?.start()
    }

    fun playSoundFromUri( resUri: Uri){

        try {
            reset() // Ensure previous MediaPlayer is released
            lastUri = resUri
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
     * Plays sound from last selected in Menu
     */
    fun playSound() {
        if (soundName == "custom") //check if last selected sound was custom sound
            playSavedUriSound() // then play from uri, otherwise just play normally
        else{
            reset() // Ensure MediaPlayer is reset before creating a new instance
            mediaPlayer = MediaPlayer.create(context, lastRes)
            mediaPlayer?.start()
        }
        mediaPlayer?.isLooping = true

    }

    /**
     * Plays sound from saved Uri
     */
    fun playSavedUriSound(){
        try {
            reset() // Ensure previous MediaPlayer is released
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, lastUri)
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
     * Briefly pauses playback of media player
     */
    fun pause() {
        if (!paused){
            mediaPlayer?.pause()
            paused = true
        }
    }

    /**
     * Continues playback of sound
     */
    fun unpause() {
        if (paused){
            paused = false
            mediaPlayer?.start()
        }
    }

    /**
     * Updates sound properties as needed
     */
    fun updateSoundProperties(gyroX: Float, gyroY: Float, gyroZ: Float) {
        val volume = calculateVolume(gyroX, gyroY)
        mediaPlayer?.setVolume(gyroX, gyroX)
    }

    /**
     * Calculates the volume from incoming gyroscope data
     */
    private fun calculateVolume(gyroX: Float, gyroY: Float): Float {
        // Map gyroscope values to a volume range (0.0f to 1.0f)
        return (gyroX.absoluteValue + gyroY.absoluteValue).coerceIn(0f, 1f)
    }


    /**
     * Check if the MediaPlayer is currently playing a sound.
     *
     * @return true if MediaPlayer is playing, false otherwise.
     */
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    /**
     * Check if playback is merely paused
     */
    fun isPaused(): Boolean {
        return paused
    }

    fun changeContext(newContext: Context) {
        context = newContext
    }
}