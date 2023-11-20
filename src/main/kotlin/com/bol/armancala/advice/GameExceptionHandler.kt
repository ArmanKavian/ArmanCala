package com.bol.armancala.advice

import com.bol.armancala.datatransfer.obj.ErrorResponseDto
import com.bol.armancala.exception.GameNotFoundException
import com.bol.armancala.exception.InvalidMoveException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GameExceptionHandler {

    private val logger = LoggerFactory.getLogger(GameExceptionHandler::class.java)

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFoundException(ex: GameNotFoundException): ResponseEntity<ErrorResponseDto> {
        logger.error("GameNotFoundException: ${ex.message}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto(ex.message))
    }

    @ExceptionHandler(InvalidMoveException::class)
    fun handleInvalidMoveException(ex: InvalidMoveException): ResponseEntity<ErrorResponseDto> {
        logger.error("InvalidMoveException: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto(ex.message))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponseDto> {
        logger.error("RuntimeException: ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDto("Internal Server Error"))
    }
}