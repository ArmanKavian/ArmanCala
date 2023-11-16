package com.bol.armancala.service

import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class GameServiceTest {

    private val gameRepository = mockk<GameRepository>()
    private val gameService = GameService(gameRepository)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `createGame should return a new game`() {
        val expectedGame = Game()
        every { gameRepository.save(any()) } returns expectedGame

        val actualGame = gameService.createGame()

        assertEquals(expectedGame, actualGame)
    }

    @Test
    fun `makeMove should update the game state`() {
        val gameId = 1L
        val pitIndex = 3
        val existingGame = Game(id = gameId)
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)
        every { gameRepository.save(any()) } answers { arg<Game>(0) }

        val updatedGame = gameService.makeMove(gameId, pitIndex)

        assertEquals(existingGame.id, updatedGame.id)
        assertEquals(existingGame.currentPlayer, updatedGame.currentPlayer)
    }

    @Test
    fun `getGame should return the game by ID`() {
        val gameId = 1L
        val expectedGame = Game(id = gameId)
        every { gameRepository.findById(gameId) } returns Optional.of(expectedGame)

        val actualGame = gameService.getGame(gameId)

        assertEquals(expectedGame, actualGame)
    }
}