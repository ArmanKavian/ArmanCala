package com.bol.armancala.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class Pit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JsonBackReference
    val game: Game? = null,

    var stones: Int = 4
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pit) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}