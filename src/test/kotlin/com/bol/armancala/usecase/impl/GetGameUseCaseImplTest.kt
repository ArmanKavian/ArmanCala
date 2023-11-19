package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class GetGameUseCaseImplTest {

    private lateinit var getGameUseCase: GetGameUseCaseImpl
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository = mockk()
        getGameUseCase = GetGameUseCaseImpl(gameRepository)
    }

    @Test
    @Transactional
    open fun `getGame should return the game when it exists`() {
        // Arrange
        val gameId = 1L
        val expectedGame = Game(id = gameId)
        every { gameRepository.findById(gameId) } returns Optional.of(expectedGame)

        // Act
        val result = getGameUseCase.getGame(gameId)

        // Assert
        assertEquals(expectedGame, result)
    }

    @Test
    @Transactional
    open fun `getGame should throw GameNotFoundException when the game is not found`() {
        // Arrange
        val gameId = 1L
        every { gameRepository.findById(gameId) } returns Optional.empty()

        // Act & Assert
        assertThrows<GameNotFoundException> {
            getGameUseCase.getGame(gameId)
        }
    }

    @Test
    @Transactional
    open fun `getGame should interact with the repository`() {
        // Arrange
        val gameId = 1L
        every { gameRepository.findById(gameId) } returns Optional.empty()

        // Act
        assertThrows<GameNotFoundException> {
            getGameUseCase.getGame(gameId)
        }

        // Assert
        verify(exactly = 1) { gameRepository.findById(gameId) }
    }
}
