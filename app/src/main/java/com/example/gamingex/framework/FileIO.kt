package com.example.gamingex.framework

import java.io.InputStream
import java.io.OutputStream

interface FileIO {
    fun getAsset(fileName: String): InputStream
    fun readFile(fileName: String): InputStream
    fun writeFile(fileName: String): OutputStream
}
