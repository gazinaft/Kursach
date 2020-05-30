package com.example.gamingex.framework.impl

import android.content.Context
import android.view.View
import com.example.gamingex.framework.Input

//the class that unites all the input handlers in one class
//used to handle accelerometer, keyboard and touch events
class AndroidInput(context: Context, view: View, scaleX: Float, scaleY: Float): Input {
    private val accelerometerHandler = AccelerometerHandler(context)
    private val touchHandler = MultiTouchHandler(view, scaleX, scaleY)
    private val keyboardHandler = KeyboardHandler(view)

    override fun isKeyPressed(keyCode: Int): Boolean = keyboardHandler.isKeyPressed(keyCode)

    override fun isTouchDown(pointer: Int): Boolean = touchHandler.isTouchDown(pointer)

    override fun getTouchX(pointer: Int): Int = touchHandler.getX(pointer)

    override fun getTouchY(pointer: Int): Int = touchHandler.getY(pointer)

    override fun getAccelX(): Float = accelerometerHandler.accelX

    override fun getAccelY(): Float = accelerometerHandler.accelY

    override fun getAccelZ(): Float = accelerometerHandler.accelZ

    override val keyEvents: List<Input.Companion.KeyEvent>
        get() = keyboardHandler.keyEvents

    override val touchEvents: List<Input.Companion.TouchEvent>
        get() = touchHandler.getTouchEvents()

}
