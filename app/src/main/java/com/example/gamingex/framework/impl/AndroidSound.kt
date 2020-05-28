package com.example.gamingex.framework.impl

import android.media.SoundPool
import com.example.gamingex.framework.Sound

//represents a class which is responsible for playing short sound effects
class AndroidSound(val soundPool: SoundPool, val soundId: Int): Sound {

    override fun play(volume: Float) {
        soundPool.play(soundId, volume, volume, 0, 0, 1f)
    }

    override fun dispose() {
        soundPool.unload(soundId)
    }
}
