package com.bol.armancala.repository

import com.bol.armancala.model.Game
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<Game, Long>