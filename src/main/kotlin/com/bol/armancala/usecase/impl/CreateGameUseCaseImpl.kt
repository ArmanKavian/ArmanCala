package com.bol.armancala.usecase.impl

import com.bol.armancala.advice.GameExceptionHandler
import com.bol.armancala.model.Game
import com.bol.armancala.model.Pit
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.CreateGameUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateGameUseCaseImpl(private val gameRepository: GameRepository)
    : CreateGameUseCase {
    private val logger = LoggerFactory.getLogger(GameExceptionHandler::class.java)

    @Transactional
    override fun createGame(): Game {
        val newGame = Game()
        initializePits(newGame)
        val savedGame = gameRepository.save(newGame)
        logger.info("New game with id ${savedGame.id} created.")
        return savedGame
    }

    private fun initializePits(newGame: Game) {
        val pits = List(14) { Pit(game = newGame) }
        pits.forEachIndexed { index, pit ->
            pit.index = index
            if (index != 6 && index != 13) {
                pit.stones = 4
            } else {
                pit.stones = 0
            }
        }
        newGame.pits = pits
    }
}