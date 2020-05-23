package com.example.gamingex.framework

abstract class Screen(protected val game: Game) {

    //method responsible for handling input dynamically
    //that`s why it has delta argument
    abstract fun update(deltaTime: Float)

    //method responsible for drawing the screen dynamically
    //that`s why it has delta argument
    abstract fun present(deltaTime: Float)

    abstract fun resume()

    abstract fun pause()

    abstract fun dispose()
}
