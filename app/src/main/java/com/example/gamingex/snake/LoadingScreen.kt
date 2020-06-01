package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Graphics.Companion.PixmapFormat
import com.example.gamingex.framework.Pixmap
import com.example.gamingex.framework.Screen

//it appears at the start of the game session and uploads all the needed resources
class LoadingScreen(game: Game): Screen(game) {

    override fun update(deltaTime: Float) {
        val g = game.getGraphics()
        val audio = game.getAudio()
        fun load(filename: String): Pixmap = g.newPixmap(filename, PixmapFormat.ARGB8888)
        with(Assets) {
            backGround = g.newPixmap("background.png", PixmapFormat.RGB565)
            logo = load("logo.png")
            mainMenu = load("mainMenu.png")
            buttons = load("buttons.png")
            help1 = load("help1.png")
            help2 = load("help2.png")
            help3 = load("help3.png")
            numbers = load("numbers.png")
            ready = load("ready.png")
            pause = load("pause.png")
            gameOver = load("gameOver.png")
            headUp = load("headUp.png")
            headDown = load("headDown.png")
            headLeft = load("headLeft.png")
            headRight = load("headRight.png")
            tail = load("tail.png")
            stain1 = load("stain1.png")
            stain2 = load("stain2.png")
            stain3 = load("stain3.png")
            turn = load("turn.png")
            down = load("down.png")

            click = audio.newSound("click.ogg")
            eat = audio.newSound("eat.ogg")
            bitten = audio.newSound("bitten.ogg")
        }
        //Settings.load(game.getFileIO())
        game.setScreen(MainMenuScreen(game))
    }

    override fun present(deltaTime: Float) = Unit

    override fun pause() = Unit

    override fun dispose() = Unit

    override fun resume() = Unit
}
