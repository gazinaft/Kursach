package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen

class HelpScreen2(game: Game): Screen(game) {

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().touchEvents
        game.getInput().keyEvents
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                if (event.x > 256 && event.y > 416) {
                    game.setScreen(HelpScreen3(game))
                    if (Settings.soundEnabled) Assets.click.play(1f)
                }
            }
        }
    }


    override fun present(deltaTime: Float) {
        val g = game.getGraphics()
        g.drawPixmap(Assets.backGround, 0, 0)
        g.drawPixmap(Assets.help2, 64, 100)
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64)
    }
    override fun resume() = Unit

    override fun pause() = Unit

    override fun dispose() = Unit
}
