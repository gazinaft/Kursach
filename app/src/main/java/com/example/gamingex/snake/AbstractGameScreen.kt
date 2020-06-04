package com.example.gamingex.snake

import android.graphics.Color
import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Graphics
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen

abstract class AbstractGameScreen(game: Game): Screen(game)  {

    abstract var state: GameState
    abstract var oldScore: Int
    abstract var score: String

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().touchEvents
        game.getInput().keyEvents

        when (state) {
            GameState.Ready -> updateReady(touchEvents)
            GameState.Running -> updateRunning(touchEvents, deltaTime)
            GameState.Paused -> updatePaused(touchEvents)
            GameState.GameOver -> updateOver(touchEvents)
        }
    }

    fun updateReady(touchEvents: Collection<Input.Companion.TouchEvent>) {
        if (touchEvents.isNotEmpty()) state = GameState.Running
    }

    abstract fun updateRunning(touchEvents: Collection<Input.Companion.TouchEvent>, deltaTime: Float)

    abstract fun updatePaused(touchEvents: Collection<Input.Companion.TouchEvent>)

    abstract fun updateOver(touchEvents: Collection<Input.Companion.TouchEvent>)

    abstract fun drawRunning(g: Graphics)

    fun drawReady(g: Graphics) {
        g.drawPixmap(Assets.ready, 47, 100)
    }

    fun drawPaused(g: Graphics) {
        g.drawPixmap(Assets.pause, 80, 100)
    }

    fun drawOver(g: Graphics) {
        g.drawPixmap(Assets.gameOver, 62, 100)
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
    }

     fun drawNumbers(g: Graphics, line: String, x: Int, y: Int) {
        var xI = x
        for (i in line.indices) {
            val char = line[i]
            if (char == ' ') {
                xI += 20
                continue
            }
            var srcWidth: Int
            var srcX: Int

            if (char == '.') {
                srcWidth = 10
                srcX = 200
            } else {
                srcX = (char - '0') * 20
                srcWidth = 20
            }
            g.drawPixmap(Assets.numbers, xI, y, srcX, 0, srcWidth, 32 )
            xI += srcWidth
        }
    }

    override fun pause() {
        if (state == GameState.Running) state = GameState.Paused

        if(state == GameState.GameOver) {
            Settings.addScore(oldScore)
            Settings.save(game.getFileIO())
        }
    }

    override fun resume() = Unit

    override fun dispose() = Unit
}
