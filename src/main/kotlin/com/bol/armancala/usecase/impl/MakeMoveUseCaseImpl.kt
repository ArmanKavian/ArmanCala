package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.exception.InvalidMoveException
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

        validateMove(game, pitIndex)

        val lastPitIndex = distributeStones(game, pitIndex)

        if (isGameOver(game)) {
            determineWinner(game)
        }

        switchPlayer(game, lastPitIndex)

        return gameRepository.save(game)
    }

    private fun distributeStones(game: Game, pitIndex: Int): Int {
        var stonesToDistribute = game.pits[pitIndex].stones
        game.pits[pitIndex].stones = 0

        var currentIndex = pitIndex
        while (stonesToDistribute > 0) {
            currentIndex = (currentIndex + 1) % 14

            if (isInOpponentBigPit(game, currentIndex)) {
                continue
            }

            game.pits[currentIndex].stones++
            stonesToDistribute--

            if (isLastStoneInEmptyPit(stonesToDistribute, game, currentIndex)) {
                captureStones(game, currentIndex)
            }
        }
        return currentIndex
    }

    private fun isInOpponentBigPit(game: Game, currentIndex: Int) =
        (game.currentPlayer == 1 && currentIndex == 13) ||
                (game.currentPlayer == 2 && currentIndex == 6)

    private fun isLastStoneInEmptyPit(
        stonesToDistribute: Int,
        game: Game,
        currentIndex: Int
    ) = stonesToDistribute == 0 && game.pits[currentIndex].stones == 1

    private fun validateMove(game: Game, pitIndex: Int) {
        if (pitIndex < 0 || pitIndex >= 14 || game.pits[pitIndex].stones == 0) {
            throw InvalidMoveException("Invalid move. Pit $pitIndex is empty.")
        }
    }

    private fun captureStones(game: Game, pitIndex: Int) {
        val oppositeIndex = 12 - pitIndex
        val capturedStones = game.pits[oppositeIndex].stones + 1

        game.pits[oppositeIndex].stones = 0
        game.pits[6].stones += capturedStones
    }

    private fun switchPlayer(game: Game, lastPitIndex: Int) {
        if (!shouldGetAnotherTurn(lastPitIndex, game.currentPlayer)) {
            return
        }
        game.currentPlayer = if (game.currentPlayer == 1) 2 else 1
    }

    private fun shouldGetAnotherTurn(lastPitIndex: Int, currentPlayer: Int) =
        (currentPlayer == 1 && lastPitIndex == 6) ||
                (currentPlayer == 2 && lastPitIndex == 13)

    private fun isGameOver(game: Game): Boolean {
        return game.pits.subList(0, 6).all { it.stones == 0 } ||
                game.pits.subList(7, 13).all { it.stones == 0 }
    }

    private fun determineWinner(game: Game) {
        val player1Stones = game.pits.subList(0, 6).sumOf { it.stones }
        val player2Stones = game.pits.subList(7, 13).sumOf { it.stones }

        game.winner = when {
            player1Stones > player2Stones -> 1
            player1Stones < player2Stones -> 2
            else -> 0
        }
    }
}