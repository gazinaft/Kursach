package com.example.gamingex.tetris

class Block(var x: Int, var y: Int, val shape: Int ) {

    companion object {
        //The shapes of blocks
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

    fun copyStructure(list: Collection<Brick> = structure): Collection<Brick> {
        val res = mutableListOf<Brick>()
        for ((index, brick) in list.withIndex()) {
            res[index] = Brick(brick.x, brick.y)
        }
        return res
    }

    fun asRight(list: ArrayList<Brick> = this.structure): ArrayList<Brick> {
        val root = list.last()
        val res = copyStructure() as ArrayList<Brick>
        for (i in 0..res.size - 2) {
            val dx = root.x - res[i].x
            val dy = root.y - res[i].y
            res[i].x = root.x + dy
            res[i].y = root.y + dx
        }
        return rebaseRoot(res)
    }

    fun asLeft(list: ArrayList<Brick> = this.structure): ArrayList<Brick> {
        val root = list.last()
        val res = copyStructure() as ArrayList<Brick>
        for (i in 0..res.size - 2) {
            val dx = root.x - res[i].x
            val dy = root.y - res[i].y
            res[i].x = root.x - dy
            res[i].y = root.y - dx
        }
        return rebaseRoot(res)
    }

    fun rebaseRoot(list: ArrayList<Brick>): ArrayList<Brick> {
        val root = list.last()
        val res = list.sortedBy { it.x }.sortedBy { it.y } as ArrayList<Brick>
        val dy = root.y - res.last().y
        moveDown(dy, res)
        return res
    }

    fun turnRight() {
        structure.clear()
        structure.addAll(asRight())
    }

    fun turnLeft() {
        structure.clear()
        structure.addAll(asLeft())
    }

    fun moveDown(step: Int = 1, list: ArrayList<Brick> = structure) {
        list.forEach { it.y += step }
    }

    fun moveUp(step: Int, list: ArrayList<Brick> = structure) {
        list.forEach { it.y -= step }
    }

}
