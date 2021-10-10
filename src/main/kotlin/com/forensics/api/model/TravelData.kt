package com.forensics.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class TravelData(
    val movements: List<Movement>,
    val startLocation: Location = Location()
)

data class Movement(
    val direction: Direction,
    val distance: Int
)

enum class Direction(val xDirectionFactor: Int, val yDirectionFactor: Int){
    NORTH(0,1),
    EAST(1,0),
    SOUTH(0, -1),
    WEST(-1, 0)
}
