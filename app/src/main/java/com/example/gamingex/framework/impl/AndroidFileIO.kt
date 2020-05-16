package com.example.gamingex.framework.impl

import android.content.Context
import com.example.gamingex.framework.FileIO
import java.io.*

class AndroidFileIO(context: Context): FileIO {
    private val assets = context.assets
    private val externalStorage = context.getExternalFilesDir(null)?.absolutePath + File.separator

    @Throws(IOException::class)
    override fun getAsset(fileName: String): InputStream = this.assets.open(fileName)

    @Throws(IOException::class)
    override fun readFile(fileName: String): InputStream = FileInputStream(externalStorage + fileName)

    @Throws(IOException::class)
    override fun writeFile(fileName: String): OutputStream = FileOutputStream(externalStorage + fileName)
}