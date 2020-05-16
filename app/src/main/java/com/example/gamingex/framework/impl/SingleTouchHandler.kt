package com.example.gamingex.framework.impl

import android.view.MotionEvent
import android.view.View
import com.example.gamingex.framework.Input.Companion.TouchEvent
import com.example.gamingex.framework.TouchHandler


class SingleTouchHandler(view: View, val scaleX: Float, val scaleY: Float): TouchHandler {
    val factory = PoolTouchFactory()
    val touchEventPool = Pool(factory, 100)
    var isTouched = false
    var touchX: Int = 0
    var touchY: Int = 0
    val touchEvents = ArrayList<TouchEvent>()
    val touchEventsBuffer = ArrayList<TouchEvent>()

    init {
        view.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        synchronized(this) {
            val touchEvent = touchEventPool.newObject()
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchEvent.type = TouchEvent.TOUCH_DOWN
                    isTouched = true
                }
                MotionEvent.ACTION_MOVE -> {
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED
                    isTouched = true
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    touchEvent.type = TouchEvent.TOUCH_UP
                    isTouched = false
                }
            }
            touchEvent.x = (event!!.x * scaleX).toInt()
            touchX = touchEvent.x
            touchEvent.y = (event.y * scaleY).toInt()
            touchY = touchEvent.y
            touchEventsBuffer.add(touchEvent)

            return true
        }
    }

    override fun isTouchDown(pointer: Int): Boolean {
        synchronized(this) {
            return if (pointer == 0) isTouched else false
        }
    }


    override fun getX(pointer: Int): Int {
        synchronized(this) {
            return touchX
        }
    }

    override fun getY(pointer: Int): Int {
        synchronized(this) {
            return touchY
        }
    }

    override fun getTouchEvents(): List<TouchEvent> {
        synchronized(this) {
            for (i in touchEvents) touchEventPool.free(i)
            touchEvents.clear()
            touchEvents.addAll(touchEventsBuffer)
            touchEventsBuffer.clear()

            return touchEvents
        }
    }
}