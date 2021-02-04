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
    var tickTime = 0f
    var activeBlock: Block? = null
    val busy = ArrayList<Brick>()
    val blockPool: Pool<Block> = Pool(BlockPoolFactory(), 4)
    var score = 0

    init {
        spawnBlock()
    }

    fun update(deltaTime: Float) {
        tickTime += deltaTime
        if (activeBlock == null) return
        while (tickTime>= tick) {
            tickTime -= tick
            activeBlock!!.moveDown()

            if (isOver()) {
                gameOver = true
                return
            }

            if (isPlaced()) spawnBlock()

            clearLines()
        }
    }

    //checks if the block can be rotated
    //because it can be blocked by other bricks
    fun canTurnRight(): Boolean = activeBlock?.asRight()?.any { field[it.x][it.y] } ?: false

    fun canTurnLeft(): Boolean = activeBlock?.asLeft()?.any { field[it.x][it.y] } ?: false

    //checks if the block is settled and can`t move further to spawn next active block
    fun isPlaced(): Boolean =
        activeBlock?.structure?.any { field[it.x][it.y+1] || it.y == WORLD_HEIGHT-1 } ?: false

    //checks if the game is over
    fun isOver(): Boolean {
        if (field.any { it[UPPER_LIM] }) gameOver = true
        return gameOver
    }

    fun clearLines(): List<Boolean> {
        val lines = List<Boolean>(field.size) {
                index -> field.map { array -> array[index] }.all { it }
        }
        return lines
    }

    //spawns a new block on top of the world
    fun spawnBlock() {
        for (i in 0 until WORLD_WIDTH) {
            for (j in 0 until WORLD_HEIGHT) {
                field[i][j] = false
            }
        }
        if (activeBlock != null) {
            busy.addAll(activeBlock!!.structure)
            blockPool.free(activeBlock!!)
        }
        for (brick in busy) {
            field[brick.x][brick.y] = true
        }
        activeBlock = blockPool.newObject()
    }

}
