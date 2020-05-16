package com.example.gamingex.framework.impl

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.gamingex.framework.*

abstract class AndroidGame: Game, Activity() {

    companion object {
        const val scrWidth = 720
        const val scrHeight = 1280
    }
    lateinit var renderView: FastRenderView
    lateinit var graphics: AndroidGraphics
    lateinit var audio: AndroidAudio
    lateinit var fileIO: AndroidFileIO
    lateinit var input: AndroidInput
    private lateinit var screen: Screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val frameBufferWidth = if (isLandscape) scrHeight else scrWidth
        val frameBufferHeight = if (isLandscape) scrWidth else scrHeight
        val frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val scaleX: Float = frameBufferWidth.toFloat() / displayMetrics.widthPixels
        val scaleY: Float = frameBufferHeight.toFloat() / displayMetrics.heightPixels

        renderView = FastRenderView(this, frameBuffer)
        graphics = AndroidGraphics(assets, frameBuffer)
        audio = AndroidAudio(this)
        fileIO = AndroidFileIO(this)
        input = AndroidInput(this, renderView, scaleX, scaleY)
        screen = getStartScreen()
        setContentView(renderView)
    }

    override fun onResume() {
        super.onResume()
        screen.resume()
        renderView.resume()
    }

    override fun onPause() {
        super.onPause()
        renderView.pause()
        screen.pause()

        if (isFinishing) screen.dispose()
    }


    override fun setScreen(screen: Screen) {
        this.screen.pause()
        this.screen.dispose()
        screen.resume()
        screen.update(0f)
        this.screen = screen
    }

    override fun getAudio(): Audio = audio

    override fun getFileIO(): FileIO = fileIO

    override fun getGraphics(): Graphics = graphics

    override fun getCurrentScreen(): Screen = screen
}