package com.bol.armancala.service

import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.CreateGameUseCase
import com.bol.armancala.usecase.GetGameUseCase
import com.bol.armancala.usecase.MakeMoveUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameService(private val gameRepository: GameRepository) :
    CreateGameUseCase,
    MakeMoveUseCase,
    GetGameUseCase {

    @Transactional
    override fun createGame(): Game {
        val game = Game()
        return gameRepository.save(game)
    }

    @Transactional
    override fun makeMove(gameId: Long, pitIndex: Int): Game {
        val game = getGame(gameId)


        return gameRepository.save(game)
    }

    @Transactional
    override fun getGame(gameId: Long): Game {
        return gameRepository.findById(gameId).orElseThrow { NoSuchElementException("Game not found") }
    }
}