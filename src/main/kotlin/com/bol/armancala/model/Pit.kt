package com.bol.armancala.model

import jakarta.persistence.*

@Entity
data class Pit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val game: Game,

    var stones: Int = 4
)