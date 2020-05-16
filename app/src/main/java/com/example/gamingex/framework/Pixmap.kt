package com.example.gamingex.framework

import com.example.gamingex.framework.Graphics.Companion.PixmapFormat

interface Pixmap {
    fun getHeight(): Int

    fun getWidth(): Int

    fun getFormat(): PixmapFormat

    fun dispose()
}
