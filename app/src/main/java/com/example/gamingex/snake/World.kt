package com.example.gamingex.snake

import kotlin.random.Random

class World {
    companion object {
        const val WORLD_WIDTH = 10
        const val WORLD_HEIGHT = 10
        const val DELTA_TIME = 0.5f
        const val DELTA_DECREMENT = 0.05f
        const val SCORE_INCREMENT = 10
    }

    var score = 0
    private var tick = DELTA_TIME
    private var tickTime = 0f
    val snake = Snake()
    private val fields = Array(WORLD_WIDTH) {BooleanArray(WORLD_HEIGHT)}
    lateinit var stain: Stain
    var gameOver = false

    init {
        placeStain()
    }

    private fun placeStain() {
        for (i in 0 until WORLD_WIDTH) {
            for (j in 0 until WORLD_HEIGHT) {
                fields[i][j] = false
            }
        }
        for (part in snake.parts) {
            fields[part.x][part.y] = true
        }

        var stainX = Random.nextInt(WORLD_WIDTH)
        var stainY = Random.nextInt(WORLD_HEIGHT)
        while (true) {
            if (!fields[stainX][stainY]) break

            ++stainX
            if (stainX >= WORLD_WIDTH) {
                stainX = 0
                ++stainY
                if (stainY >= WORLD_HEIGHT) {
                    stainY = 0
                }
            }
        }
        stain = Stain(stainX, stainY, Random.nextInt(3))
    }

    fun update(deltaTime: Float) {
        if (gameOver) return

        tickTime+= deltaTime

        while (tickTime > tick) {
            tickTime -= tick
            snake.advance()

            if (snake.isBitten()) {
                gameOver = true
                return
            }

            val head = snake.parts.first()
            if (head.x == stain.x && head.y == stain.y) {
                score += SCORE_INCREMENT
                snake.eat()

                if (snake.parts.size == WORLD_HEIGHT * WORLD_WIDTH) {
                    gameOver = true
                    return
                }

                placeStain()

                if (score % 100 == 0 && tick - DELTA_DECREMENT > 0) tick -= DELTA_DECREMENT
            }
        }
    }

}
