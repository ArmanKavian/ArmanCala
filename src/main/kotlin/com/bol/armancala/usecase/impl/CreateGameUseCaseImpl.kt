package com.bol.armancala.usecase.impl

import com.bol.armancala.model.Game
import com.bol.armancala.model.Pit
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.CreateGameUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateGameUseCaseImpl(private val gameRepository: GameRepository)
    : CreateGameUseCase {

    @Transactional
    override fun createGame(): Game {
        val newGame = Game()
        initializePits(newGame)
        return gameRepository.save(newGame)
    }

    private fun initializePits(newGame: Game) {
        val pits = List(14) { Pit(game = newGame) }
        pits.forEachIndexed { index, pit ->
            if (index != 6 && index != 13) {
                pit.stones = 4
            }
        }
        newGame.pits = pits
    }
}