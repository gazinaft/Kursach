package com.example.gamingex.tetris

class Block(var x: Int, var y: Int, val shape: Int ) {

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
        const val CURVY1 = 4
        //  0
        //000
        const val CURVY2 = 5
        // 0
        //000
        const val LADDER = 6
    }
    //structure of a particular block
    // which is responsible for the order and place of the block in a game
    //depends on a shape of each block
    //the x and y are the coordinates of lower right brick
    val structure = when(shape){
        SQUARE ->
            arrayListOf<Brick>(Brick(x - 1, y - 1), Brick(x, y - 1), Brick(x - 1, y))
        LINE ->
            arrayListOf<Brick>(Brick(x, y - 3), Brick(x, y - 2), Brick(x, y - 1))
        ZIGZAG1 ->
            arrayListOf<Brick>(Brick(x, y - 1), Brick(x + 1, y - 1), Brick(x - 1, y))
        ZIGZAG2 ->
            arrayListOf<Brick>(Brick(x - 2, y - 1), Brick(x - 1, y - 1), Brick(x - 1, y))
        CURVY1 ->
            arrayListOf<Brick>(Brick(x - 2, y - 1), Brick(x - 2, y), Brick(x - 1, y))
        CURVY2 ->
            arrayListOf<Brick>(Brick(x, y - 1), Brick(x - 2, y), Brick(x - 1, y))
        LADDER ->
            arrayListOf<Brick>(Brick(x - 1, y - 1), Brick(x - 2, y), Brick(x - 1, y))
        else ->
            throw IllegalStateException("There are only 7 shapes of blocks in Tetris")
    }.apply { add(Brick(x, y)) }

    fun rotated(): Collection<Brick> = when (shape) {
        SQUARE ->
            structure.toMutableList()

        else ->
            throw IllegalStateException("There are only 7 shapes of blocks in Tetris")
    }

    fun moveDown() {
        structure.forEach { it.y += 1 }
    }

}
