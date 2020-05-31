package com.example.gamingex.comps

interface Physics: Position, Velocity {
    companion object {
        const val G = 10
    }
}
