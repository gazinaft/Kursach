package com.example.gamingex.snake

import com.example.gamingex.framework.FileIO
import java.io.*
import java.lang.NumberFormatException

object Settings {
    var soundEnabled = true
    val highscores = IntArray(5){100-it*15}

    fun load(files: FileIO) {
        var inp: BufferedReader? = null
        try {
            inp = BufferedReader(InputStreamReader(files.readFile(".snake")))
            soundEnabled = inp.readLine()?.toBoolean() ?: true
            for (i in 0..4) {
                highscores[i] = inp.readLine().toInt()
            }
        } catch (e: IOException) {}
        catch (e: NumberFormatException) {}
        finally {
            try {
                inp?.close()
            } catch (e: IOException) {}
        }
    }

    fun save(files: FileIO) {
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(files.writeFile(".snake")))
            out.write(soundEnabled.toString())
            for (i in 0..4) out.write(highscores[i].toString())
        } catch (e: IOException){
        } finally {
            try {
                out?.close()
            } catch (e: IOException){}
        }
    }

    fun addScore(score: Int) {
        for (i in 0..4) {
            if (highscores[i] < score) {
                for (j in 4 downTo i)
                    highscores[j] = highscores[j - 1]
                highscores[i] = score
                break
            }
        }
    }


}