package com.example.gamingex.framework

//used as an alternative to classical activities
//coordinates all the modules of a framework
interface Game {

    fun getFileIO(): FileIO

    fun getGraphics(): Graphics

    fun getAudio(): Audio

    fun getInput(): Input

    fun getCurrentScreen(): Screen

    fun setScreen(screen: Screen)

    fun getStartScreen(): Screen

}