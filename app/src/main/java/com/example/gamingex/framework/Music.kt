package com.example.gamingex.framework

interface Music {

    fun play()

    fun pause()

    fun stop()

    fun setLooping(looping: Boolean)

    fun setVolume(volume: Float)

    fun isPlaying(): Boolean

    fun isStopped(): Boolean

    fun isLooping(): Boolean

    fun dispose()
}
