package com.bol.armancala.usecase.impl

import com.bol.armancala.createSampleGame
import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.exception.InvalidMoveException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
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
        val existingGame = createSampleGame(id = gameId)
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

    @Test
    fun `makeMove should throw InvalidMoveException when trying to move stones from an empty pit`() {
        // Arrange
        val gameId = 1L
        val currentPlayer = 1
        val pitIndex = 9
        val existingGame = createSampleGame(id = gameId, currentPlayer = currentPlayer)
        existingGame.pits[pitIndex].stones = 0
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)

        // Act & Assert
        assertThrows<InvalidMoveException> {
            makeMoveUseCase.makeMove(gameId, pitIndex)
        }
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 0) { gameRepository.save(any()) }
    }

    @Test
    fun `makeMove should throw InvalidMoveException when trying to move opponent's stones`() {
        // Arrange
        val gameId = 1L
        val currentPlayer = 2
        val pitIndex = 4
        val existingGame = createSampleGame(id = gameId, currentPlayer = currentPlayer)
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)

        // Act & Assert
        assertThrows<InvalidMoveException> {
            makeMoveUseCase.makeMove(gameId, pitIndex)
        }
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 0) { gameRepository.save(any()) }
    }

    @Test
    fun `makeMove should switch player when not getting another turn`() {
        // Arrange
        val gameId = 1L
        val currentPlayer = 1
        val pitIndex = 5
        val existingGame = createSampleGame(id = gameId, currentPlayer = currentPlayer)
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)
        val savedGameSlot = slot<Game>()
        every { gameRepository.save(capture(savedGameSlot)) } answers { savedGameSlot.captured }

        // Act
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)

        // Assert
        assertEquals(2, updatedGame.currentPlayer)
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 1) { gameRepository.save(existingGame) }
    }

    @Test
    fun `makeMove should capture stones and give another turn when the last stone lands in the player's big pit`() {
        // Arrange
        val gameId = 1L
        val currentPlayer = 1
        val stonesInEachPit = 0
        val pitIndex = 5
        val stonesInPitIndex = 1
        val existingGame = createSampleGame(id = gameId,
            currentPlayer = currentPlayer, stonesInEachPit = stonesInEachPit)
        existingGame.pits[pitIndex].stones = stonesInPitIndex
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)
        val savedGameSlot = slot<Game>()
        every { gameRepository.save(capture(savedGameSlot)) } answers { savedGameSlot.captured }

        // Act
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)

        // Assert
        assertEquals(existingGame.currentPlayer, updatedGame.currentPlayer)
        assertEquals(stonesInPitIndex - 1, updatedGame.pits[pitIndex].stones)
        assertEquals(1, updatedGame.pits[6].stones)
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 1) { gameRepository.save(existingGame) }
    }

    @Test
    fun `makeMove should determine the winner when the game is over`() {
        // Arrange
        val gameId = 1L
        val pitIndex = 5
        val currentPlayer = 1
        val existingGame = createSampleGame(id = gameId, currentPlayer = currentPlayer)
        existingGame.pits.subList(0, pitIndex).forEach { it.stones = 0 }
        existingGame.pits[6].stones = 14
        existingGame.pits[13].stones = 9
        existingGame.pits[pitIndex].stones = 1
        every { gameRepository.findById(gameId) } returns Optional.of(existingGame)
        val savedGameSlot = slot<Game>()
        every { gameRepository.save(capture(savedGameSlot)) } answers { savedGameSlot.captured }

        // Act
        val updatedGame = makeMoveUseCase.makeMove(gameId, pitIndex)

        // Assert
        assertEquals(2, updatedGame.winner)
        verify(exactly = 1) { gameRepository.findById(gameId) }
        verify(exactly = 1) { gameRepository.save(existingGame) }
    }
}
