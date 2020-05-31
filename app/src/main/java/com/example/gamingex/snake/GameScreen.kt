package com.example.gamingex.snake

import android.graphics.Color
import com.example.gamingex.framework.*
import com.example.gamingex.snake.Snake.Companion.DOWN
import com.example.gamingex.snake.Snake.Companion.LEFT
import com.example.gamingex.snake.Snake.Companion.RIGHT
import com.example.gamingex.snake.Snake.Companion.UP

class GameScreen(game: Game): Screen(game) {

    enum class GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    val world = World()
    var state = GameState.Ready
    var oldScore = 0
    var score = "0"

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


    private fun updateReady(touchEvents: Collection<Input.Companion.TouchEvent>) {
        if (touchEvents.isNotEmpty()) state = GameState.Running
    }

    private fun updateRunning(touchEvents: Collection<Input.Companion.TouchEvent>, deltaTime: Float) {
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP && event.x < 64 && event.y < 64) {
                if (Settings.soundEnabled) Assets.click.play(1f)
                state = GameState.Paused
                return
            }
            if (event.type == Input.Companion.TouchEvent.TOUCH_DOWN) {
                if (event.x < 64 && event.y > game.getGraphics().getHeight() - 64) {
                    world.snake.turnLeft()
                }
                val wid = game.getGraphics().getWidth()
                val hei = game.getGraphics().getHeight()
                if (event.x > wid - 64 && event.y > hei - 64) {
                    world.snake.turnRight()
                }
            }
        }
        world.update(deltaTime)

        if (world.gameOver) {
            if (Settings.soundEnabled) Assets.bitten.play(1f)
            state = GameState.GameOver
        }
        if (oldScore != world.score) {
            oldScore = world.score
            if (Settings.soundEnabled) Assets.eat.play(1f)
            score = oldScore.toString()
        }
    }

    private fun updatePaused(touchEvents: Collection<Input.Companion.TouchEvent>) {
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                if (event.x in 80..240 && event.y in 100..148) {
                    if (Settings.soundEnabled) Assets.click.play(1f)
                    state = GameState.Running
                    return
                }
                if (event.x in 80..240 && event.y in 148..196){
                    if (Settings.soundEnabled) Assets.click.play(1f)
                    game.setScreen(MainMenuScreen(game))
                    return
                }
            }
        }
    }

    private fun updateOver(touchEvents: Collection<Input.Companion.TouchEvent>) {
        for (event in touchEvents) {
            if (event.type == Input.Companion.TouchEvent.TOUCH_UP) {
                if (event.x in 128..192 && event.y in 200..264) {
                    if (Settings.soundEnabled) Assets.click.play(1f)
                    game.setScreen(MainMenuScreen(game))
                    return
                }
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()

        g.drawPixmap(Assets.backGround, 0, 0)

        drawWorld(world)
        g.drawLine(0, g.getHeight() - 60, g.getWidth(), g.getHeight() - 60, Color.BLACK)

        when (state) {
            GameState.Running -> drawRunning(g)
            GameState.Ready -> drawReady(g)
            GameState.Paused -> drawPaused(g)
            GameState.GameOver -> drawOver(g)
        }
        val numX = g.getWidth() / 2 - score.length * 10
        drawNumbers(g, score, numX, g.getHeight() - 42)
    }

    private fun drawWorld(world: World) {
        val snake = world.snake
        val g = game.getGraphics()
        val head = snake.parts.first()
        val stain = world.stain

        val stainPixmap: Pixmap = when(stain.type) {
            Stain.TYPE_1 -> Assets.stain1
            Stain.TYPE_2 -> Assets.stain2
            Stain.TYPE_3 -> Assets.stain3
            else -> throw IllegalStateException("Stain must be one of 3 types")
        }
        val stX = stain.x * 32
        val stY = stain.y * 32

        g.drawPixmap(stainPixmap, stX, stY)

        for (i in 1 until snake.parts.size) {
            g.drawPixmap(Assets.tail, snake.parts[i].x * 32, snake.parts[i].y * 32)
        }

        val headPixmap = when (snake.direction) {
            UP -> Assets.headUp
            LEFT -> Assets.headLeft
            DOWN -> Assets.headDown
            RIGHT -> Assets.headRight
            else -> throw IllegalStateException("Head can be in only 4 directions")
        }

        val headX = head.x * 32 + 16
        val headY = head.y * 32 + 16
        val headPosX = headX - headPixmap.getWidth() / 2
        val headPosY = headY - headPixmap.getHeight() / 2

        g.drawPixmap(headPixmap, headPosX, headPosY)
    }

    private fun drawReady(g: Graphics) {
        g.drawPixmap(Assets.ready, 47, 100)
    }


    private fun drawRunning(g: Graphics) {
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64)
        g.drawPixmap(Assets.buttons, 0, g.getHeight()-64, 64, 64, 64, 64)
        g.drawPixmap(Assets.buttons, 256, g.getHeight()-64, 0, 64, 64, 64)
    }

    private fun drawPaused(g: Graphics) {
        g.drawPixmap(Assets.pause, 80, 100)
    }

    private fun drawOver(g: Graphics) {
        g.drawPixmap(Assets.gameOver, 62, 100)
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
    }

    private fun drawNumbers(g: Graphics, line: String, x: Int, y: Int) {
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
