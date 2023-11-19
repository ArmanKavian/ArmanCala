package com.bol.armancala

import com.bol.armancala.model.Game
import com.bol.armancala.model.Pit

fun createSampleGame(id: Long = 1L,
                     currentPlayer : Int = 1,
                     stonesInEachPit: Int = 4) : Game {
    val newGame = Game(id = id, currentPlayer = currentPlayer)

    val pits = List(14) { Pit(game = newGame) }
    pits.forEachIndexed { index, pit ->
        if (index != 6 && index != 13) {
            pit.stones = stonesInEachPit
        } else {
            pit.stones = 0
        }
    }
    newGame.pits = pits

    return newGame
}