package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.MakeMoveUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MakeMoveUseCaseImpl(private val gameRepository: GameRepository)
    : MakeMoveUseCase {

    @Transactional
    override fun makeMove(gameId: Long, pitIndex: Int): Game {
        val game = gameRepository.findById(gameId).orElseThrow { GameNotFoundException(gameId) }

        return gameRepository.save(game)
    }
}