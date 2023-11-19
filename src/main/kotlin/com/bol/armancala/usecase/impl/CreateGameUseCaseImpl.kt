package com.bol.armancala.usecase.impl

import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.CreateGameUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateGameUseCaseImpl(private val gameRepository: GameRepository)
    : CreateGameUseCase {

    @Transactional
    override fun createGame(): Game {
        val game = Game()
        return gameRepository.save(game)
    }
}