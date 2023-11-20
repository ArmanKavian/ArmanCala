package com.bol.armancala.controller

import com.bol.armancala.createGameDto
import com.bol.armancala.datatransfer.adapter.DataTransferAdapter
import com.bol.armancala.model.Game
import com.bol.armancala.usecase.CreateGameUseCase
import com.bol.armancala.usecase.GetGameUseCase
import com.bol.armancala.usecase.MakeMoveUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class GameControllerTest {

    @MockK
    private lateinit var dataTransferAdapter: DataTransferAdapter

    @MockK
    private lateinit var createGameUseCase: CreateGameUseCase

    @MockK
    private lateinit var makeMoveUseCase: MakeMoveUseCase

    @MockK
    private lateinit var getGameUseCase: GetGameUseCase

    @InjectMockKs
    private lateinit var gameController: GameController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build()
    }

    @Test
    fun `createGame should return a new game`() {
        // Arrange
        val expectedGame = Game()
        val expectedGameDto = createGameDto(expectedGame)
        every { createGameUseCase.createGame() } returns expectedGame
        every { dataTransferAdapter.toGameDto(any()) } returns expectedGameDto

        // Act & Assert
        mockMvc.perform(post("/api/games"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expectedGame.id))
    }

    @Test
    fun `makeMove should update the game state`() {
        // Arrange
        val gameId = 1L
        val pitIndex = 3
        val expectedGame = Game()
        val expectedGameDto = createGameDto(expectedGame)
        every { makeMoveUseCase.makeMove(gameId, pitIndex) } returns expectedGame
        every { dataTransferAdapter.toGameDto(any()) } returns expectedGameDto

        // Act & Assert
        mockMvc.perform(post("/api/games/{gameId}/move/{pitIndex}", gameId, pitIndex))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expectedGame.id))
    }

    @Test
    fun `getGame should return the game by ID`() {
        // Arrange
        val gameId = 1L
        val expectedGame = Game()
        val expectedGameDto = createGameDto(expectedGame)
        every { getGameUseCase.getGame(gameId) } returns expectedGame
        every { dataTransferAdapter.toGameDto(any()) } returns expectedGameDto

        // Act & Assert
        mockMvc.perform(get("/api/games/{gameId}", gameId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expectedGame.id))
    }
}
