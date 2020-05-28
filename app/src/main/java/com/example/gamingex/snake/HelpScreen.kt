package com.example.gamingex.snake

import com.example.gamingex.framework.Game
import com.example.gamingex.framework.Input
import com.example.gamingex.framework.Screen


//this is first of 3 helpScreens which will help to deal with gameplay
class HelpScreen(game: Game): HelpScreenAbstract(game) {
    override val order: Int = 1
}
