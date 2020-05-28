package com.example.gamingex.framework


interface Input {
    companion object {
        class KeyEvent {
            companion object {
                const val KEY_UP = 1
                const val KEY_DOWN = 0
            }
             var type: Int = 0
             var keyCode: Int = 0
             var keyChar: Char = '0'
        }
        class TouchEvent {
            companion object {
                const val TOUCH_DOWN = 0
                const val TOUCH_UP = 1
                const val TOUCH_DRAGGED = 2
            }
            var type: Int = 0
            var x: Int = 0
            var y: Int = 0
            var pointer: Int = 0
        }
    }

    fun isKeyPressed(keyCode: Int): Boolean

    fun isTouchDown(pointer: Int): Boolean

    fun getTouchX(pointer: Int): Int

    fun getTouchY(pointer: Int): Int

    fun getAccelX(): Float

    fun getAccelY(): Float

    fun getAccelZ(): Float

    val keyEvents: List<KeyEvent>

    val touchEvents: List<TouchEvent>
}
