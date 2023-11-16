package com.bol.armancala.usecase

import com.bol.armancala.model.Game

fun interface MakeMoveUseCase {
    fun makeMove(gameId: Long, pitIndex: Int): Game
}