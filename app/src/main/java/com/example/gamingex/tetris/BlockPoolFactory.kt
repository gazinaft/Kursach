package com.example.gamingex.tetris

import com.example.gamingex.framework.impl.Pool
import com.example.gamingex.framework.impl.PoolFactory
import kotlin.random.Random

class BlockPoolFactory: PoolFactory<Block> {
    override fun createObject(): Block {
        return Block(TetrisWorld.WORLD_WIDTH/2, 0, Random.nextInt(7))
    }
}
