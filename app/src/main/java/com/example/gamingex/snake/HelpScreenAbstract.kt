package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen

abstract class HelpScreenAbstract(game: Game): Screen(game) {

    abstract val order: Int

    fun update(deltaTime: Float, number: Int) {
        val touchEvents = game.getInput().touchEvents
        game.getInput().keyEvents
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                if (event.x > 256 && event.y > game.getGraphics().getHeight()-64) {
                    game.setScreen(screenFactory(number, game))
                    if (Settings.soundEnabled) Assets.click.play(1f)
                }
            }
        }
    }

    override fun update(deltaTime: Float) {
        update(deltaTime, order)
    }

    fun presentAbstract(number: Int) {
        val g = game.getGraphics()
        val asset = when (number) {
            1-> Assets.help1
            2-> Assets.help2
            3-> Assets.help3
            else -> throw IllegalStateException("There are only 3 helpscreens")
        }

        g.drawPixmap(Assets.backGround, 0, 0)
        g.drawPixmap(asset, 64, 100)
        g.drawPixmap(Assets.buttons, 256, game.getGraphics().getHeight()-64, 0, 64, 64, 64)
    }

    override fun present(deltaTime: Float) {
        presentAbstract(order)
    }

    fun screenFactory(int: Int, game: Game): Screen {
        return when (int) {
            1 -> HelpScreen2(game)
            2 -> HelpScreen3(game)
            3 -> MainMenuScreen(game)
            else -> throw IllegalStateException("there are only 3 screens")
        }
    }

    override fun resume() = Unit

    override fun pause() = Unit

    override fun dispose() = Unit

}
