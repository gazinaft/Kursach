package com.example.gamingex.framework

import android.view.View.OnTouchListener
import com.example.gamingex.framework.Input.Companion.TouchEvent

interface TouchHandler: OnTouchListener {
    fun isTouchDown(pointer: Int): Boolean

    fun getX(pointer: Int): Int

    fun getY(pointer: Int): Int

    fun getTouchEvents(): List<TouchEvent>
}
