package com.bol.armancala.model

import jakarta.persistence.*

@Entity
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var pits: List<Pit> = emptyList(),

    var currentPlayer: Int = 1
) {
    init {
        pits = List(14) { Pit(game = this) }
    }
}