package com.example.gamingex.tetris

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Graphics
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen
import com.example.gamingex.snake.AbstractGameScreen
import com.example.gamingex.snake.GameState

class TetrisScreen(game: Game): AbstractGameScreen(game) {

    override var oldScore: Int = 0
    override var score: String = "0"
    override var state: GameState = GameState.Ready

    override fun updateRunning(touchEvents: Collection<Input.Companion.TouchEvent>, deltaTime: Float) {
        TODO("Not yet implemented")
    }

    override fun updatePaused(touchEvents: Collection<Input.Companion.TouchEvent>) {
        TODO("Not yet implemented")
    }

    override fun updateOver(touchEvents: Collection<Input.Companion.TouchEvent>) {
        TODO("Not yet implemented")
    }

    override fun present(deltaTime: Float) {
        TODO("Not yet implemented")
    }

    override fun drawRunning(g: Graphics) {
        TODO("Not yet implemented")
    }
}
