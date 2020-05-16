package com.example.gamingex.framework.impl

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.SurfaceHolder
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class FastRenderView(private val game: AndroidGame, val frameBuffer: Bitmap): SurfaceView(game), Runnable {

    private var renderThread: Thread? = null
    @Volatile var running: Boolean = false
    private var hold: SurfaceHolder = holder

    fun resume() {
        running = true
        renderThread = Thread(this)
        renderThread?.start()
    }

    override fun run() {
        val dstRect = Rect()
        var startTime: Long = System.nanoTime()
        while (running) {
            if (!hold.surface.isValid) continue

            val deltaTime: Float = (System.nanoTime() - startTime)/1_000_000_000f

            startTime = System.nanoTime()

            game.getCurrentScreen().update(deltaTime)
            game.getCurrentScreen().present(deltaTime)

            val canvas = hold.lockCanvas()
            canvas.getClipBounds(dstRect)
            canvas.drawBitmap(frameBuffer, null, dstRect, null)
            hold.unlockCanvasAndPost(canvas)
        }
    }


    fun pause() {
        running = false
        while (true) {
            try {
                renderThread?.join()
                break
            } catch (e: InterruptedException) {}
        }
    }


}
