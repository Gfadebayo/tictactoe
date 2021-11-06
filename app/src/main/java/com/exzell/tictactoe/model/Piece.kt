package com.exzell.tictactoe.model

import java.util.*

class Piece(val name: String, val levelNumber: Int){

    val moves: LinkedList<Int> = LinkedList<Int>()

    //Once its confirmed that the moves made are in any of these orders, a winner is found
    val winMove1 = mutableListOf(Pair(1, 2), Pair(1, 4), Pair(1, 5), Pair(2, 5), Pair(3, 5), Pair(3, 6), Pair(4, 5), Pair(7, 8))

    val winMove2 = mutableListOf(Pair(2, 3), Pair(4, 7), Pair(5, 9), Pair(5, 8), Pair(5, 7), Pair(6, 9), Pair(5, 6), Pair(8, 9))

    val winMove3 = mutableListOf(Pair(1, 3), Pair(1, 7), Pair(1, 9), Pair(2, 8), Pair(3, 7), Pair(3, 9), Pair(4, 6), Pair(7, 9))
}
