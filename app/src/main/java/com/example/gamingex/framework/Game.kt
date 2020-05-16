package com.example.gamingex.framework

interface Game {

    fun getFileIO(): FileIO

    fun getGraphics(): Graphics

    fun getAudio(): Audio

    fun getCurrentScreen(): Screen

    fun setScreen(screen: Screen)

    fun getStartScreen(): Screen

}