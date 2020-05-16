package com.example.gamingex.framework.impl

import android.graphics.Bitmap
import com.example.gamingex.framework.Graphics.Companion.PixmapFormat
import com.example.gamingex.framework.Pixmap

//a wrapper class responsible for asset representation
class AndroidPixmap(val bitmap: Bitmap, private val format: PixmapFormat): Pixmap {

    override fun getFormat(): PixmapFormat = format

    override fun getHeight(): Int = bitmap.height

    override fun getWidth(): Int = bitmap.width

    override fun dispose() {
        bitmap.recycle()
    }
}
