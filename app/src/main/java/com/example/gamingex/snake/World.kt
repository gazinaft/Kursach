package com.example.gamingex.snake

import kotlin.random.Random

class World {
    companion object {
        const val WORLD_WIDTH = 10
        const val WORLD_HEIGHT = 10
        const val DELTA_TIME = 0.5f
        const val DELTA_DECREMENT = 0.05f
        const val SCORE_INCREMENT = 10
        const val seed = 15
    }
    var score = 0
    val random = Random(seed)
    var tick = DELTA_TIME
    var tickTime = 0f
    val snake = Snake()
    val fields = List(WORLD_WIDTH) {BooleanArray(WORLD_HEIGHT)}
    lateinit var stain: Stain
    var gameOver = false

    private fun placeStain() {
        for (i in 0 until WORLD_WIDTH) {
            for (j in 0 until WORLD_HEIGHT) {
                fields[i][j] = false
            }
        }
        for (part in snake.parts) {
            fields[part.x][part.y] = true
        }

        var stainX = random.nextInt(WORLD_WIDTH)
        var stainY = random.nextInt(WORLD_HEIGHT)
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
        stain = Stain(stainX, stainY, random.nextInt(3))
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