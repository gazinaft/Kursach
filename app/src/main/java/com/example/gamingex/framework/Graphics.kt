package com.example.gamingex.framework


interface Graphics {
    companion object {
        enum class PixmapFormat {
            ARGB8888, ARGB4444, RGB565
        }
    }
    fun newPixmap(filename: String, format: PixmapFormat): Pixmap

    fun clear(color: Int)

    fun drawPixel(x: Int, y: Int, color: Int)

    fun drawLine(x1: Int, y1: Int, x2: Int, y2:Int, color: Int)

    fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int)

    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int)

    fun getWidth(): Int

    fun getHeight(): Int

}