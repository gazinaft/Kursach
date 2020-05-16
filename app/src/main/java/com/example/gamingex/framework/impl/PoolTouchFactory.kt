package com.example.gamingex.framework.impl

import com.example.gamingex.framework.Input

class PoolTouchFactory : PoolFactory<Input.Companion.TouchEvent> {
    override fun createObject(): Input.Companion.TouchEvent = Input.Companion.TouchEvent()
}
