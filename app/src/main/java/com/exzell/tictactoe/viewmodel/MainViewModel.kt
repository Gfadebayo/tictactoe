package com.exzell.tictactoe.viewmodel

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.exzell.tictactoe.R
import com.exzell.tictactoe.fragment.GameFragment
import java.io.File

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val context = app.applicationContext

    lateinit var soundPool: SoundPool

    var soundIdX: Int = 0

    var soundIdO: Int = 0

    var useSound: Boolean = true

    fun prepareSound(){
        Thread{
            val fileX = File(context.getExternalFilesDir("sounds"), "x.mp3")
            val fileY = File(context.getExternalFilesDir("sounds"), "y.mp3")

            soundPool = SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build())
                    .setMaxStreams(1)
                    .build().apply {
                        soundIdX = if(!fileX.exists()) load(context, R.raw.sound_x, 1) else load(fileX.path, 1)
                        soundIdO = if(!fileY.exists()) load(context, R.raw.sound_o, 1) else load(fileY.path, 1)
                    }
        }.start()
    }

    fun playSound(pieceNumber: Int) {
        if(!useSound) return

        soundPool.play(if(pieceNumber == GameFragment.LEVEL_X) soundIdX else soundIdO,
                1F, 1F, 1, 0, 1F)
    }

    fun enableSound(enable: Boolean) {
        useSound = enable
    }
}