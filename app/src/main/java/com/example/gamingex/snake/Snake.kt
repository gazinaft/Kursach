package com.example.gamingex.snake

class Snake {
    companion object {
        const val UP = 0
        const val LEFT = 1
        const val DOWN = 2
        const val RIGHT = 3
    }

    val parts = ArrayList<SnakeBody>()
    var direction = UP

    init {
        parts.add(SnakeBody(5, 6))
        parts.add(SnakeBody(5, 7))
        parts.add(SnakeBody(5, 8))
    }

    fun turnLeft() {
        ++direction
        if (direction > RIGHT) direction = UP

    }

    fun turnRight() {
        --direction
        if (direction < UP) direction = RIGHT
    }

    fun eat() {
        val end = parts.last()
        parts.add(SnakeBody(end.x, end.y))
    }

    fun advance() {
        val head = parts.first()
        for (i in parts.size-1 downTo 1) {
            parts[i].x = parts[i-1].x
            parts[i].y = parts[i-1].y
        }

        when (direction) {
            UP -> --head.y
            LEFT -> --head.x
            DOWN -> ++head.y
            RIGHT -> ++head.x
        }

        when {
            head.x < 0 -> head.x = 9
            head.x > 9 -> head.x = 0
            head.y < 0 -> head.y = 9
            head.y > 9 -> head.y = 0
        }
    }

    fun isBitten(): Boolean {
        val head = parts.first()
        for (i in 1 until parts.size) {
            if (parts[i].x == head.x && parts[i].y == head.y) return true
        }
        return false
    }

}
