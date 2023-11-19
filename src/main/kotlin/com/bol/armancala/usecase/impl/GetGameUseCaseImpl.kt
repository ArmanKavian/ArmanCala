package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.GetGameUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetGameUseCaseImpl(private val gameRepository: GameRepository) :
    GetGameUseCase {

    @Transactional
    override fun getGame(gameId: Long): Game {
        return gameRepository.findById(gameId).orElseThrow { GameNotFoundException(gameId) }
    }
}