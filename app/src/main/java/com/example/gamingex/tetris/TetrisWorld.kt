package com.example.gamingex.tetris

import androidx.core.util.Pools
import com.example.gamingex.framework.impl.Pool
import kotlin.random.Random

class TetrisWorld {

    companion object {
        const val WORLD_WIDTH = 20
        const val WORLD_HEIGHT = 20
        const val TICK_TIME = 0.5f
        const val TICK_DECREMENT = 0.05f
        const val SCORE_INCREMENT = 10
        const val UPPER_LIM = 1
    }

    val field = Array(WORLD_WIDTH) {BooleanArray(WORLD_HEIGHT)}
    var gameOver = false
    var tick = TICK_TIME
    var activeBlock: Block? = null
    val busy = ArrayList<Brick>()
    val blockPool: Pool<Block> = Pool(BlockPoolFactory(), 4)

    init {
        spawnBlock()
    }

    fun update(deltaTime: Float) {

    }

    //checks if the game is over
    fun isOver(): Boolean {
        gameOver = true
        return field.any { it[UPPER_LIM] }
    }

    //spawns a new block on top of the world
    fun spawnBlock() {
        for (i in 0 until WORLD_WIDTH) {
            for (j in 0 until WORLD_HEIGHT) {
                field[i][j] = false
            }
        }
        busy.addAll(activeBlock?.structure ?: emptyList())
        for (brick in busy) {
            field[brick.x][brick.y] = true
        }
        activeBlock?.run { blockPool.free(activeBlock!!) }
        activeBlock = blockPool.newObject()
    }

}
