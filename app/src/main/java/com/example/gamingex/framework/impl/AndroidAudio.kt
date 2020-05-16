package com.example.gamingex.framework.impl


import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.gamingex.framework.Audio
import com.example.gamingex.framework.Music
import com.example.gamingex.framework.Sound
import java.io.IOException

//class responsible for interactions with audio files
class AndroidAudio(context: Context): Audio {
    private val assets = context.assets

    private val effAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()

    private val effSoundPool = SoundPool.Builder()
        .setAudioAttributes(effAttributes)
        .setMaxStreams(6)
        .build()


    override fun newMusic(fileName: String): Music {
        try {
            val assetDescriptor: AssetFileDescriptor = assets.openFd(fileName)
            return AndroidMusic(assetDescriptor)
        } catch (e: IOException) {
            throw RuntimeException("Can`t upload music $fileName")
        }
    }

    override fun newSound(fileName: String): Sound {
        try {
            val assetDescriptor = assets.openFd(fileName)
            val soundId = effSoundPool.load(assetDescriptor, 0)
            return AndroidSound(effSoundPool, soundId)
        } catch (e: IOException) {
            throw RuntimeException("Can`t upload sound $fileName")
        }
    }
}




