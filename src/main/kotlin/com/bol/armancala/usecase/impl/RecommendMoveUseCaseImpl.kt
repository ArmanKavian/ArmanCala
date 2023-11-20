package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.repository.GameRepository
import com.bol.armancala.usecase.RecommendMoveUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendMoveUseCaseImpl(private val gameRepository: GameRepository) :
    RecommendMoveUseCase {

    @Transactional
    override fun recommend(gameId: Long): Int {
        gameRepository.findById(gameId).orElseThrow { GameNotFoundException(gameId) }

        return -1
    }
}