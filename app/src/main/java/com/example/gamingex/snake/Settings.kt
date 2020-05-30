package com.example.gamingex.snake

import com.example.gamingex.framework.FileIO
import java.io.*
import java.lang.NumberFormatException

//stores the settings values for the whole game
object Settings {
    //sound status
    var soundEnabled = true

    //default leaderboard
    val highscores = IntArray(5){100-it*10}

    //loads the previous settings and leaderboards
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
        try {
            inp?.close()
        } catch (e: IOException) {}

    }

    //saves the settings and leaderboards
    fun save(files: FileIO) {
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(files.writeFile(".snake")))
            out.write(soundEnabled.toString())
            for (i in 0..4) out.write(highscores[i].toString())
        } catch (e: IOException){}
        try {
            out?.close()
        } catch (e: IOException){}
    }

    //updates the leaderboards
    fun addScore(score: Int) {
        for (i in 0..4) {
            if (highscores[i] < score) {
                for (j in 4 downTo i+1)
                    highscores[j] = highscores[j - 1]
                highscores[i] = score
                break
            }
        }
    }

}
