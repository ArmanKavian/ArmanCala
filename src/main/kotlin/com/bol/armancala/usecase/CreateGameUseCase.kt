package com.bol.armancala.usecase

import com.bol.armancala.model.Game

fun interface CreateGameUseCase {
    fun createGame(): Game
}