package com.example.gamingex.framework

interface Audio {
    fun newMusic(fileName: String): Music

    fun newSound(fileName: String): Sound
}

