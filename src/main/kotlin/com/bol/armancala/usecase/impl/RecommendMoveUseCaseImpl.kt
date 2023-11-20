package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.RecommendMoveUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendMoveUseCaseImpl(private val gameRepository: GameRepository) :
    RecommendMoveUseCase {

    @Transactional
    override fun recommend(gameId: Long): Int {
        val game = gameRepository.findById(gameId).orElseThrow { GameNotFoundException(gameId) }

        val validMoves = findValidMoves(game)

        return findBestMove(game, validMoves)
    }

    private fun findBestMove(game: Game, validMoves: List<Int>): Int {
        var bestMove = -1
        var maxCapture = 0
        var maxStonesForExtraTurn = 0

        validMoves.forEach { move ->
            val potentialCapture = calculatePotentialCapture(game, move)
            val stonesForExtraTurn = calculateStonesForExtraTurn(game, move)

            if (potentialCapture > maxCapture ||
                (potentialCapture == maxCapture && stonesForExtraTurn > maxStonesForExtraTurn)) {
                bestMove = move
                maxCapture = potentialCapture
                maxStonesForExtraTurn = stonesForExtraTurn
            }
        }

        return bestMove
    }

    private fun calculatePotentialCapture(game: Game, move: Int): Int {
        val stones = game.pits[move].stones
        var currentIndex = move
        var capturedStones = 0

        for (i in 1..stones) {
            currentIndex = (currentIndex + 1) % game.pits.size

            if (i == stones && game.pits[currentIndex].stones == 0) {
                //Two pits are big pits
                val oppositePitIndex = game.pits.size - 2 - currentIndex

                val isOnPlayersSide = (game.currentPlayer == 1 && currentIndex < 6) ||
                        (game.currentPlayer == 2 && currentIndex in 7..12)

                if (isOnPlayersSide) {
                    capturedStones = game.pits[oppositePitIndex].stones + 1
                }
            }
        }
        return capturedStones
    }

    private fun calculateStonesForExtraTurn(game: Game, move: Int): Int {
        val stones = game.pits[move].stones
        val totalPits = game.pits.size
        val endPitIndex = (move + stones) % totalPits

        val playerBigPitIndex = if (game.currentPlayer == 1) 6 else 13

        return if (endPitIndex == playerBigPitIndex) 1 else 0
    }

    private fun findValidMoves(game: Game): List<Int> {
        return game.pits.indices.filter { index ->
            isMoveValidForPlayer(game, index)
        }
    }

    private fun isMoveValidForPlayer(game: Game, index: Int): Boolean {
        val isNotBigPit = index != 6 && index != 13
        val hasStones = game.pits[index].stones != 0
        val isCurrentPlayerPit = (game.currentPlayer == 1 && index < 6) ||
                (game.currentPlayer == 2 && index in 7..13)

        return isNotBigPit && hasStones && isCurrentPlayerPit
    }

}