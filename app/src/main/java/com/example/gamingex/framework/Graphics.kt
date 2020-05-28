package com.example.gamingex.framework

//interface responsible for all graphical manipulations
interface Graphics {
    companion object {
        //used to describe color format of Pixmap
        enum class PixmapFormat {
            ARGB8888, ARGB4444, RGB565
        }
    }
    //returns a new asset(drawing) resource
    //which is used to draw complicated drawings
    fun newPixmap(filename: String, format: PixmapFormat): Pixmap

    //fills the frameBuffer with a selected color
    fun clear(color: Int)

    fun drawPixel(x: Int, y: Int, color: Int)

    fun drawLine(x1: Int, y1: Int, x2: Int, y2:Int, color: Int)

    //draws a rectangle of selected color
    fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int)

    //draws a selected fragment of an asset
    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int, srcWidth: Int, srcHeight: Int)

    //draws the whole asset
    fun drawPixmap(pixmap: Pixmap, x: Int, y: Int)

    //returns resolution of the screen (width)
    fun getWidth(): Int

    //returns resolution of the screen (height)
    fun getHeight(): Int

}
