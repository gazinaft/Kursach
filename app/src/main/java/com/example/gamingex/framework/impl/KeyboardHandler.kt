package com.example.gamingex.framework.impl

import android.view.KeyEvent
import android.view.View
import com.example.gamingex.framework.Input


class KeyEventFactory: PoolFactory<Input.Companion.KeyEvent> {
    override fun createObject(): Input.Companion.KeyEvent = Input.Companion.KeyEvent()
}

class KeyboardHandler(view: View): View.OnKeyListener {
    companion object {
        const val MAX_SIZE = 100
    }
    val pressedKeys = BooleanArray(128)
    val factory = KeyEventFactory()
    val keyEventPool = Pool<Input.Companion.KeyEvent>(factory, MAX_SIZE)
    val keyEventsBuffer = ArrayList<Input.Companion.KeyEvent>()

    val keyEvents = ArrayList<Input.Companion.KeyEvent>()
        get() {
            synchronized(this) {
                for (i in field) keyEventPool.free(i)

                field.clear()
                field.addAll(keyEventsBuffer)
                keyEventsBuffer.clear()
                return field
            }
        }

    init {
        with(view) {
            setOnKeyListener(this@KeyboardHandler)
            isFocusableInTouchMode = true
            requestFocus()
        }
    }


    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event == null) return false
        synchronized(this) {
            val keyEvent = keyEventPool.newObject()
            keyEvent.keyCode = keyCode
            keyEvent.keyChar = event.unicodeChar.toChar()
            if (event.action == KeyEvent.ACTION_DOWN) {
                keyEvent.type = Input.Companion.KeyEvent.KEY_DOWN
                if (keyCode in 0..127) pressedKeys[keyCode] = true
            }
            if (event.action == KeyEvent.ACTION_UP) {
                keyEvent.type = Input.Companion.KeyEvent.KEY_UP
                if (keyCode in 0..127) pressedKeys[keyCode] = false
            }
            keyEventsBuffer.add(keyEvent)
        }
        return false
    }

    fun isKeyPressed(keyCode: Int): Boolean = if (keyCode in 0..127) pressedKeys[keyCode] else false

}
