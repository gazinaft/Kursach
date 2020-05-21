package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen

class MainMenuScreen(game: Game): Screen(game) {

    override fun update(deltaTime: Float) {
        val g = game.getGraphics()
        val touchEvents = game.getInput().touchEvents
        game.getInput().keyEvents

        for (i in touchEvents.indices) {
            val event = touchEvents[i]
            if(event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                when {
                    inBounds(event, 0, g.getHeight()-64, 64, 64) -> {
                        Settings.soundEnabled = !Settings.soundEnabled
                        if (Settings.soundEnabled) Assets.click.play(1f)
                    }
                    inBounds(event,  64, 220, 192, 42) -> {
                        game.setScreen(GameScreen(game))
                        if (Settings.soundEnabled) Assets.click.play(1f)
                        return
                    }
                    inBounds(event, 64, 220 + 42, 192, 42) -> {

                    }
                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()
        g.drawPixmap(Assets.backGround, 0, 0)
        g.drawPixmap(Assets.logo, 32, 20)
        g.drawPixmap(Assets.mainMenu, 64, 220)
        if (Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0,416, 0, 0, 64, 64)
        else
            g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64)
    }

    private fun inBounds(event: Input.Companion.TouchEvent, x: Int, y: Int, width: Int, height: Int): Boolean =
        event.x in x until x+width && event.y in y until y + height


    override fun pause() {
        Settings.save(game.getFileIO())
    }

    override fun resume() = Unit

    override fun dispose() = Unit
}