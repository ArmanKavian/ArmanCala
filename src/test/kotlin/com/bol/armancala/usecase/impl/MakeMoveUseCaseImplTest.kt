package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class MakeMoveUseCaseImplTest {

    private val gameRepository: GameRepository = mockk()
    private val makeMoveUseCase = MakeMoveUseCaseImpl(gameRepository)

    @Test
    @Transactional
    open fun `makeMove should update the game state and return the updated game`() {
        // Arrange
        val gameId = 1L
        val pitIndex = 3
        val existingGame = Game(id = gameId)
        val savedGame = existingGame.copy()
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)
        every { gameRepository.save(any()) } returns savedGame

        // Act
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)

        // Assert
        assertEquals(savedGame, updatedGame)
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 1) { gameRepository.save(existingGame) }
    }

    @Test
    @Transactional
    open fun `makeMove should throw GameNotFoundException when the game is not found`() {
        // Arrange
        val gameId = 1L
        val pitIndex = 3
        every { gameRepository.findById(gameId) } returns Optional.empty()

        // Act & Assert
        assertThrows<GameNotFoundException> {
            makeMoveUseCase.makeMove(gameId, pitIndex)
        }
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 0) { gameRepository.save(any()) }
    }
}
