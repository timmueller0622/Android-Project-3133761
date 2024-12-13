package com.example.androidproject3133761

import android.content.Context
import android.media.MediaPlayer

public class SoundPlayer (private val context: Context) {

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
}