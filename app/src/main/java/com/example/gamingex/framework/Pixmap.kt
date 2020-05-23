package com.example.gamingex.framework

import com.example.gamingex.framework.Graphics.Companion.PixmapFormat

//a wrapper interface for a Bitmap
interface Pixmap {

    fun getHeight(): Int

    fun getWidth(): Int

    //returns its color format:
    // ARGB888, ARGB4444 or RGB565
    fun getFormat(): PixmapFormat

    //unloads the resources to free RAM space
    fun dispose()
}
