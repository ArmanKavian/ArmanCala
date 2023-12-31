package com.bol.armancala.api

import com.bol.armancala.datatransfer.obj.GameDto
import com.bol.armancala.datatransfer.obj.MoveRecommendationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/games")
interface GameApi {
    @PostMapping
    fun createGame(): ResponseEntity<GameDto>
    @GetMapping("/{gameId}")
    fun getGame(@PathVariable gameId: Long): ResponseEntity<GameDto>
    @PostMapping("/{gameId}/move/{pitIndex}")
    fun makeMove(@PathVariable gameId: Long, @PathVariable pitIndex: Int): ResponseEntity<GameDto>
    @GetMapping("/{gameId}/recommend")
    fun recommend(@PathVariable gameId: Long): ResponseEntity<MoveRecommendationDto>
}