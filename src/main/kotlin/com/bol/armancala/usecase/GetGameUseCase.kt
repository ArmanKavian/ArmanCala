package com.bol.armancala.usecase

import com.bol.armancala.model.Game

fun interface GetGameUseCase {
    fun getGame(gameId: Long): Game
}