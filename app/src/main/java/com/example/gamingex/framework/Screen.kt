package com.example.gamingex.framework

abstract class Screen(protected val game: Game) {

    abstract fun update(deltaTime: Float)

    abstract fun present(deltaTime: Float)

    abstract fun resume()

    abstract fun pause()

    abstract fun dispose()
}
