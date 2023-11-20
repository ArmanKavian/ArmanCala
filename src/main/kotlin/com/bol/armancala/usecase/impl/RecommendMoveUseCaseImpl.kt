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

        val move = if (validMoves.isNotEmpty()) {
            validMoves.random()
        } else {
            -1
        }
        return move
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