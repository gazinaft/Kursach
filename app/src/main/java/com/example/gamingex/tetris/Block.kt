package com.example.gamingex.tetris

class Block(x: Int, y: Int, shape: Int ) {
    companion object {
        //00
        //00
        const val SQUARE = 0
        //0
        //0
        //0
        //0
        const val LINE = 1
        // 00
        //00
        const val ZIGZAG1 = 2
        //00
        // 00
        const val ZIGZAG2 = 3
        //0
        //000
        const val CURVY = 4
        // 0
        //000
        const val LADDER = 5
    }



}