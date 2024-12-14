package com.example.androidproject3133761

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

public class SoundPlayer (private var context: Context) {

    private var mediaPlayer: MediaPlayer? = null

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
     * Releases the MediaPlayer resources when they are no longer needed.
     */
    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
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