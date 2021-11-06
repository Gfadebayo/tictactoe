package com.exzell.tictactoe

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import com.exzell.tictactoe.fragment.GameFragment
import com.exzell.tictactoe.model.Piece
import kotlin.collections.ArrayList

class GameViewModel : ViewModel() {

    val pieceX = Piece("X", GameFragment.LEVEL_X)

    val pieceO = Piece("O", GameFragment.LEVEL_Y)

    lateinit var winningMove1: Pair<Int, Int>

    fun checkWinningMoves(piece: Piece, moves: MutableList<Int>): Boolean{
        moves.sort()

        val pair1 = Pair(moves[0], moves[1])
        val pair2 = Pair(moves[1], moves[2])
        val pair3 = Pair(moves[0], moves[2])

        val win = piece.winMove1.any {
            it.first.compareTo(pair1.first) == 0 && it.second.compareTo(pair1.second) == 0
        } && piece.winMove2.any {
            it.first.compareTo(pair2.first) == 0 && it.second.compareTo(pair2.second) == 0
        } && piece.winMove3.any {
            it.first.compareTo(pair3.first) == 0 && it.second.compareTo(pair3.second) == 0
        }

        if(win) winningMove1 = pair1

        return win
    }

    /**
     * Creates pairs of 3 with the moves already made and the new one
     */
    fun checkIfWin(pieceNumber: Int, addon: Int): Boolean{
        val piece = if(pieceNumber == GameFragment.LEVEL_X) pieceX else pieceO

        with(piece.moves) {

            when {
                size < 2 -> return false
                size == 2 -> return checkWinningMoves(piece, mutableListOf(this[0], this[1], addon))
                else -> {
                    ArrayList(piece.moves).let {
                        for (i in 0 until it.size) {
                            for (j in i + 1 until it.size) {

                                if (checkWinningMoves(piece, mutableListOf(it[i], it[j], addon))) return true
                            }
                        }
                    }

                    return false
                }
            }
        }
    }

    fun addMove(pieceNumber: Int, newMove: Int){
        val piece = if(pieceNumber == GameFragment.LEVEL_X) pieceX else pieceO
        val otherPiece = if(pieceNumber == GameFragment.LEVEL_X) pieceO else pieceX

        piece.moves.push(newMove)

        Thread {
            otherPiece.winMove1.removeIf {
                it.first == newMove || it.second == newMove
            }

            otherPiece.winMove2.removeIf {
                it.first == newMove || it.second == newMove
            }

            otherPiece.winMove3.removeIf {
                it.first == newMove || it.second == newMove
            }
        }.start()
    }
}