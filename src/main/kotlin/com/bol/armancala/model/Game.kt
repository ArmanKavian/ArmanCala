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
    init {
        pits = List(14) { Pit(game = this) }

        pits.forEachIndexed { index, pit ->
            if (index != 6 && index != 13) {
                pit.stones = 4
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Game) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}