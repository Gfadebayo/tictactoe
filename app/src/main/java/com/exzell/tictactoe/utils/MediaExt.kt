package com.exzell.tictactoe.utils

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun saveInputStream(inputStream: InputStream, filename: String){

    val file = File(filename)
    if(!file.exists()) file.createNewFile()

    FileOutputStream(file).apply {
        val array = ByteArray(inputStream.available())
        inputStream.read(array)

        write(array)

        close()
        inputStream.close()
    }

}