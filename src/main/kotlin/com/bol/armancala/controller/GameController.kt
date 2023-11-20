package com.bol.armancala.controller

import com.bol.armancala.api.GameApi
import com.bol.armancala.datatransfer.adapter.DataTransferAdapter
import com.bol.armancala.datatransfer.obj.GameDto
import com.bol.armancala.datatransfer.obj.MoveRecommendationDto
import com.bol.armancala.usecase.CreateGameUseCase
import com.bol.armancala.usecase.GetGameUseCase
import com.bol.armancala.usecase.MakeMoveUseCase
import com.bol.armancala.usecase.RecommendMoveUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api/games")
class GameController(
    private val dataTransferAdapter: DataTransferAdapter,
    private val createGameUseCase: CreateGameUseCase,
    private val makeMoveUseCase: MakeMoveUseCase,
    private val getGameUseCase: GetGameUseCase,
    private val recommendMoveUseCase: RecommendMoveUseCase
) : GameApi {
    override fun createGame(): ResponseEntity<GameDto> {
        val newGame = createGameUseCase.createGame()
        val gameDto = dataTransferAdapter.toGameDto(newGame)
        return ResponseEntity.ok(gameDto)
    }

    override fun makeMove(gameId: Long, pitIndex: Int): ResponseEntity<GameDto> {
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)
        val gameDto = dataTransferAdapter.toGameDto(updatedGame)
        return ResponseEntity.ok(gameDto)
    }

    override fun getGame(gameId: Long): ResponseEntity<GameDto> {
        val game = getGameUseCase.getGame(gameId)
        val gameDto = dataTransferAdapter.toGameDto(game)
        return ResponseEntity.ok(gameDto)
    }

    override fun recommend(gameId: Long): ResponseEntity<MoveRecommendationDto> {
        val recommendedMove = recommendMoveUseCase.recommend(gameId)
        val moveRecommendationDto = dataTransferAdapter.toMoveRecommendationDto(gameId, recommendedMove)
        return ResponseEntity.ok(moveRecommendationDto)
    }
}