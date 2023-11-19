package com.bol.armancala.controller

import com.bol.armancala.api.GameApi
import com.bol.armancala.model.Game
import com.bol.armancala.usecase.CreateGameUseCase
import com.bol.armancala.usecase.GetGameUseCase
import com.bol.armancala.usecase.MakeMoveUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/games")
class GameController(
    private val createGameUseCase: CreateGameUseCase,
    private val makeMoveUseCase: MakeMoveUseCase,
    private val getGameUseCase: GetGameUseCase
) : GameApi {
    override fun createGame(): ResponseEntity<Game> {
        val game = createGameUseCase.createGame()
        return ResponseEntity.ok(game)
    }

    override fun makeMove(gameId: Long, pitIndex: Int): ResponseEntity<Game> {
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)
        return ResponseEntity.ok(updatedGame)
    }

    override fun getGame(gameId: Long): ResponseEntity<Game> {
        val game = getGameUseCase.getGame(gameId)
        return ResponseEntity.ok(game)
    }
}