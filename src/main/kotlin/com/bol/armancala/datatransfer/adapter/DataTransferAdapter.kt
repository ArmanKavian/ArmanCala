package com.bol.armancala.datatransfer.adapter

import com.bol.armancala.datatransfer.obj.GameDto
import com.bol.armancala.model.Game
import org.springframework.stereotype.Component

@Component
class DataTransferAdapter {

    fun toGameDto(game: Game) = GameDto(game.id, game.pits.map { it.stones }, game.currentPlayer, game.winner)
}