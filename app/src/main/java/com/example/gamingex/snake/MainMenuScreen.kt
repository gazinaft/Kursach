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
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                when {
                    //checks whether a user clicked on "Volume" button
                    inBounds(event, 0, g.getHeight()-64, 64, 64) -> {
                        Settings.soundEnabled = !Settings.soundEnabled
                        if (Settings.soundEnabled) Assets.click.play(1f)
                    }
                    //checks whether a user clicked on "Play" button
                    inBounds(event,  64, 220, 192, 42) -> {
                        game.setScreen(GameScreen(game))
                        if (Settings.soundEnabled) Assets.click.play(1f)
                        return
                    }
                    //checks whether a user clicked on "Highscores" button
                    inBounds(event, 64, 220 + 42, 192, 42) -> {
                        game.setScreen(HighscoreScreen(game))
                        if (Settings.soundEnabled) Assets.click.play(1f)
                        return
                    }
                    //checks whether a user clicked on "Help" button
                    inBounds(event, 64, 220 + 84, 192, 42) -> {
                        game.setScreen(HelpScreen(game))
                        if (Settings.soundEnabled) Assets.click.play(1f)
                        return
                    }
                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()
        g.drawPixmap(Assets.backGround, 0, 0)
        g.drawPixmap(Assets.logo, 32, 20)

        //draws buttons for main menu
        g.drawPixmap(Assets.mainMenu, 64, 220)

        //draws a sound icon depending on a volume settings
        val yDraw = g.getHeight()-64
        val srcX = if (Settings.soundEnabled) 0 else 64
        g.drawPixmap(Assets.buttons, 0, yDraw, srcX, 0, 64, 64)
    }

    private fun inBounds(event: Input.Companion.TouchEvent, x: Int, y: Int, width: Int, height: Int): Boolean =
        event.x in x until x+width && event.y in y until y + height

    //saves all the settings
    override fun pause() {
        Settings.save(game.getFileIO())
    }

    override fun resume() = Unit

    override fun dispose() = Unit
}
