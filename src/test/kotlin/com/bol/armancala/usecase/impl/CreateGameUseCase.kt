package com.bol.armancala.usecase.impl

import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

open class CreateGameUseCaseImplTest {

    private val gameRepository: GameRepository = mockk()
    private val createGameUseCase = CreateGameUseCaseImpl(gameRepository)

    @Test
    @Transactional
    open fun `createGame should return a new game`() {
        // Arrange
        val gameId = 1L
        val game = Game(id = gameId)
        every { gameRepository.save(any()) } returns game

        // Act
        val createdGame = createGameUseCase.createGame()

        // Assert
        assertEquals(game, createdGame)
        verify(exactly = 1) { gameRepository.save(any()) }
    }
}
