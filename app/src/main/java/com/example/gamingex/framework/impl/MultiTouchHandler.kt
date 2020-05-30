package com.example.gamingex.framework.impl

import android.view.MotionEvent
import android.view.View
import com.example.gamingex.framework.Input.Companion.TouchEvent
import com.example.gamingex.framework.TouchHandler


class MultiTouchHandler(view: View, val scaleX: Float, val scaleY: Float): TouchHandler {
    companion object{
        const val MAX_TOUCH = 10
    }
    val isTouched = BooleanArray(MAX_TOUCH)
    val touchX = IntArray(MAX_TOUCH)
    val touchY = IntArray(MAX_TOUCH)
    val id = IntArray(MAX_TOUCH)
    val touchEvents = ArrayList<TouchEvent>()
    val touchEventsBuffer = ArrayList<TouchEvent>()
    val factory = PoolTouchFactory()
    val touchEventPool = Pool(factory, 100)

    init {
        view.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == null || event == null) return false
        synchronized(this) {
            val action = event.action and MotionEvent.ACTION_MASK
            val pointerIndex = (event.action and MotionEvent.ACTION_POINTER_INDEX_MASK) shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
            val pointerCount = event.pointerCount
            lateinit var touchEvent: TouchEvent

            for (i in 0 until MAX_TOUCH) {
                if (i >= pointerCount) {
                    isTouched[i] = false
                    id[i] = -1
                    continue
                }
                val pointerId = event.getPointerId(i)
                if (event.action != MotionEvent.ACTION_MOVE && i != pointerIndex) continue

                when (action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                        touchEvent = touchEventPool.newObject()
                        with(touchEvent) {
                            type = TouchEvent.TOUCH_DOWN
                            pointer = pointerId
                            x = (event.x * scaleX).toInt()
                            touchX[i] = x
                            y = (event.y * scaleY).toInt()
                            touchY[i] = y
                        }
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }
                    MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                        touchEvent = touchEventPool.newObject()
                        with(touchEvent) {
                            type = TouchEvent.TOUCH_UP
                            pointer = pointerId
                            x = (event.x * scaleX).toInt()
                            touchX[i] = x
                            y = (event.y * scaleY).toInt()
                            touchY[i] = y
                        }
                        isTouched[i] = false
                        id[i] = -1
                        touchEventsBuffer.add(touchEvent)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        touchEvent = touchEventPool.newObject()
                        with(touchEvent) {
                            type = TouchEvent.TOUCH_DRAGGED
                            pointer = pointerId
                            x = (event.x * scaleX).toInt()
                            touchX[i] = x
                            y = (event.y * scaleY).toInt()
                            touchY[i] = y
                        }
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }
                }
            }
            return true
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

    override fun getX(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index in 0 until MAX_TOUCH) touchX[index] else 0
        }
    }

    override fun getY(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index in 0 until MAX_TOUCH) touchY[index] else 0
        }
    }

    override fun isTouchDown(pointer: Int): Boolean {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index in 0 until MAX_TOUCH) isTouched[index] else false
        }
    }

    private fun getIndex(pointer: Int): Int {
        for (i in 0 until MAX_TOUCH) {
            if (id[i] == pointer) return i
        }
        return -1
    }

}
