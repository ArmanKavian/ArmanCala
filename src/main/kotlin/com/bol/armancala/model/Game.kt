package com.bol.armancala.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    var pits: List<Pit> = emptyList(),

    var currentPlayer: Int = 1,

    var winner: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Game(id=$id, currentPlayer=$currentPlayer, winner=$winner)"
    }
}