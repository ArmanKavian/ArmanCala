package com.bol.armancala.exception

class GameNotFoundException(gameId: Long)
    : NoSuchElementException("Game with id=$gameId not found") {
}