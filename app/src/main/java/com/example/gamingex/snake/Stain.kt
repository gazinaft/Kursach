package com.example.gamingex.snake

//class represents a stain
//which a the snake eats and grows
class Stain(val x: Int, val y: Int, val type: Int) {
    companion object {
        const val TYPE_1 = 0
        const val TYPE_2 = 1
        const val TYPE_3 = 2
    }
}
