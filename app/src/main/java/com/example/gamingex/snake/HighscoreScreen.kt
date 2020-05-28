package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Graphics
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen

class HighscoreScreen(game: Game): Screen(game) {

    val scores = List<String>(5) { "${(it+1)}. ${Settings.highscores[it]}" }

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().touchEvents
        val g = game.getGraphics()

        //clear the buffer of keyEvents
        game.getInput().keyEvents
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP &&
                    event.x > g.getWidth()-64 && event.y > g.getHeight()-64) {
                if (Settings.soundEnabled) Assets.click.play(1f)
                game.setScreen(MainMenuScreen(game))
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()
        g.drawPixmap(Assets.backGround, 0, 0)
        g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42)

        var y = 100
        for (i in scores) {
            drawNumbers(g, i, 20, y)
            y += 50
        }

        g.drawPixmap(Assets.buttons, g.getWidth()-64, g.getHeight()-64,64, 64, 64, 64)

    }

    fun drawNumbers(g: Graphics, line: String, x: Int, y: Int) {
        var xI = x
        for (i in line.indices) {
            val char = line[i]
            if (char == ' ') {
                xI += 20
                continue
            }
            var srcWidth = 0
            var srcX = 0

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

    override fun pause() = Unit

    override fun resume() = Unit

    override fun dispose() = Unit

}
