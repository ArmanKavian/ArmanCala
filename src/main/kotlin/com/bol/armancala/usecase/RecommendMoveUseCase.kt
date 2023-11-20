package com.bol.armancala.usecase

fun interface RecommendMoveUseCase {
    fun recommend(gameId: Long): Int
}