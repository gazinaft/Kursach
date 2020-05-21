package com.example.gamingex.framework.impl

import android.content.res.AssetManager
import android.graphics.*
import androidx.annotation.Px
import com.example.gamingex.framework.Graphics
import com.example.gamingex.framework.Graphics.Companion.PixmapFormat
import com.example.gamingex.framework.Pixmap
import java.io.IOException
import java.io.InputStream


//module responsible for graphics
class AndroidGraphics(val assets: AssetManager, val frameBuffer: Bitmap): Graphics {

    private val canvas = Canvas(frameBuffer)
    private val paint = Paint()
    private val srcRect = Rect()
    private val dstRect = Rect()

    //returns a new instance of asset
    override fun newPixmap(filename: String, format: PixmapFormat): Pixmap {
        var form = PixmapFormat.ARGB8888
        val config: Bitmap.Config = when (format) {
            PixmapFormat.ARGB8888, PixmapFormat.ARGB4444 -> Bitmap.Config.ARGB_8888
            PixmapFormat.RGB565 -> Bitmap.Config.RGB_565
        }
        val options = BitmapFactory.Options()
        options.inPreferredConfig = config
        var inp: InputStream? = null
        val bitmap: Bitmap
        try {
            inp = assets.open(filename)
            bitmap = BitmapFactory.decodeStream(inp)
            if (bitmap == null) throw RuntimeException("Couldn`t load bitmap from asset $filename")
        } catch (e: IOException) {
            throw RuntimeException("Couldn`t load bitmap from asset $filename")
        } finally {
            if (inp != null) try {
                inp.close()
            } catch (e: IOException) {}
        }
        if (bitmap.config == Bitmap.Config.RGB_565) form = PixmapFormat.RGB565
        return AndroidPixmap(bitmap, form)
    }


    //clears the framebuffer
    override fun clear(color: Int) {
        canvas.drawRGB((color and 0xff000) shr 16, (color and 0xff00) shr 8, (color and 0xff))
    }

    override fun drawPixel(x: Int, y: Int, color: Int) {
        paint.color = color
        canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
    }

    override fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int, color: Int) {
        paint.color = color
        canvas.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    override fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int) {
        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + width - 1).toFloat(), (y + height - 1).toFloat(), paint)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
        srcWidth: Int, srcHeight: Int) {

        srcRect.left = srcX
        srcRect.top = srcY
        srcRect.right = srcX + srcWidth - 1
        srcRect.bottom = srcY + srcHeight - 1

        dstRect.left = x
        dstRect.top = y
        dstRect.right = x + srcWidth - 1
        dstRect.bottom = y + srcHeight - 1

        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, srcRect, dstRect, null)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int) {
        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, x.toFloat(), y.toFloat(), null)
    }

    override fun getHeight(): Int = frameBuffer.height

    override fun getWidth(): Int = frameBuffer.width
}