package com.bol.armancala.api

import com.bol.armancala.model.Game
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/games")
interface GameApi {
    @PostMapping
    fun createGame(): ResponseEntity<Game>
    @GetMapping("/{gameId}")
    fun getGame(@PathVariable gameId: Long): ResponseEntity<Game>
    @PostMapping("/{gameId}/move/{pitIndex}")
    fun makeMove(@PathVariable gameId: Long, @PathVariable pitIndex: Int): ResponseEntity<Game>
}