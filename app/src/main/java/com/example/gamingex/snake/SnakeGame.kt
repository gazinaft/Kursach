package com.example.gamingex.snake

import com.example.gamingex.framework.Screen
import com.example.gamingex.framework.impl.AndroidGame

class SnakeGame: AndroidGame() {
    override fun getStartScreen(): Screen = LoadingScreen(this)
}