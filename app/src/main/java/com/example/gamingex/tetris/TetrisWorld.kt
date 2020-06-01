package com.example.gamingex.tetris

import kotlin.random.Random

class TetrisWorld {

    companion object {
        const val WORLD_WIDTH = 20
        const val WORLD_HEIGHT = 20
        const val TICK_TIME = 0.5f
        const val TICK_DECREMENT = 0.05f
        const val SCORE_INCREMENT = 10
    }

    val field = Array(WORLD_WIDTH) {BooleanArray(WORLD_HEIGHT)}
    var gameOver = false
    var tick = TICK_TIME
    lateinit var activeBlock: Block
    val busy = ArrayList<Brick>()

    init {
        spawnBlock()
    }

    fun update(deltaTime: Float) {

    }

    //checks if the game is over
    fun over(): Boolean {

    }

    //spawns a new block on top of the world
    fun spawnBlock() {
        for (i in 0 until WORLD_WIDTH) {
            for (j in 0 until WORLD_HEIGHT) {
                field[i][j] = false
            }
            activeBlock = Block(WORLD_WIDTH/2, 0, Random.nextInt(7))
            busy.addAll(activeBlock.structure)
        }
    }


}
