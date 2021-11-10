package com.exzell.tictactoe.viewmodel

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import com.exzell.tictactoe.fragment.GameFragment

class MainViewModel : ViewModel() {

    lateinit var soundPool: SoundPool

    var soundIdX: Int = 0

    var soundIdO: Int = 0

    fun prepareSound(pieceX: Int, pieceO: Int, context: Context){
        Thread{
            soundPool = SoundPool.Builder().setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build())
                    .setMaxStreams(1)
                    .build().apply {
                        soundIdX = load(context, pieceX, 1)
                        soundIdO = load(context, pieceO, 1)
                    }
        }.start()
    }

    fun playSound(pieceNumber: Int) {
        soundPool.play(if(pieceNumber == GameFragment.LEVEL_X) soundIdX else soundIdO,
                1F, 1F, 1, 0, 1F)
    }
}