package com.example.gamingex.framework


interface Sound {

    //plays the soundEffect
    fun play(volume: Float)

    //unloads the soundEffect from the memory
    //to free RAM
    fun dispose()
}
