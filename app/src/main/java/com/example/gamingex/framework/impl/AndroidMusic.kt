package com.example.gamingex.framework.impl

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.example.gamingex.framework.Music
import java.io.IOException


//class which is responsible for playing background music
class AndroidMusic(descriptor: AssetFileDescriptor): Music, MediaPlayer.OnCompletionListener {
    private var isPrepared = false
    private val mediaPlayer = MediaPlayer()

    init {
        try {
            mediaPlayer.setDataSource(descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length)
            mediaPlayer.prepare()
            isPrepared = true
            mediaPlayer.setOnCompletionListener(this)
        } catch (e: IOException) {
            throw RuntimeException("Can`t upload music")
        }
    }

    override fun dispose() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun isLooping(): Boolean = mediaPlayer.isLooping

    override fun isPlaying(): Boolean = mediaPlayer.isPlaying

    override fun isStopped(): Boolean = !isPrepared

    override fun setLooping(looping: Boolean) {
        mediaPlayer.isLooping = looping
    }

    override fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }

    override fun play() {
        if (mediaPlayer.isPlaying) return

        try {
            synchronized(this) {
                if (!isPrepared) mediaPlayer.prepare()
                mediaPlayer.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun stop() {
        mediaPlayer.stop()

        synchronized(this) {
            isPrepared = false
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        synchronized(this) {
            isPrepared = false
        }
    }

    override fun pause() {
        mediaPlayer.pause()
    }

}