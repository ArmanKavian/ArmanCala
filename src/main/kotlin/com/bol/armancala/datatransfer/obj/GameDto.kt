package com.bol.armancala.datatransfer.obj

data class GameDto(
    val id: Long,
    val pits: List<Int>,
    val currentPlayer: Int,
    val winner: Int?
)