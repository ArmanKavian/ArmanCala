package com.bol.armancala.usecase.impl

import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.model.Game
import com.bol.armancala.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

open class RecommendMoveUseCaseImplTest {

    private lateinit var recommendMoveUseCase: RecommendMoveUseCaseImpl
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository = mockk()
        recommendMoveUseCase = RecommendMoveUseCaseImpl(gameRepository)
    }

    @Test
    fun `recommend should return a pit index`() {
        // Arrange
        val gameId = 1L
        val game = Game()
        every { gameRepository.findById(gameId) } returns Optional.of(game)

        // Act
        val recommendedMove = recommendMoveUseCase.recommend(gameId)

        // Assert
        assertEquals(true,recommendedMove in -1..13)
    }

    @Test
    fun `recommend should throw GameNotFoundException when game does not exist`() {
        // Arrange
        val gameId = 1L
        every { gameRepository.findById(gameId) } returns Optional.empty()

        // Act & Assert
        assertThrows(GameNotFoundException::class.java) {
            recommendMoveUseCase.recommend(gameId)
        }
    }
}
