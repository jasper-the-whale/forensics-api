package com.forensics.api.processors

import com.forensics.api.model.Direction
import com.forensics.api.model.Movement
import com.forensics.api.model.TravelData
import org.springframework.stereotype.Component

@Component
class TravelDataGenerator {
    private val minMovements: Int = 5
    private val maxMovements: Int = 10
    private val minDistance: Int = 1
    private val maxDistance: Int = 5

    fun newTravelData(): TravelData {
        val totalMovements = (minMovements..maxMovements).random()
        val movements = (1..totalMovements).toList().map {
            randomMovement()
        }
        return TravelData(movements = movements)
    }

    private fun randomMovement() =
        Movement(direction = randomDirection(), distance = randomDistance())

    private fun randomDirection(): Direction = Direction.values().random()

    private fun randomDistance(): Int = (minDistance..maxDistance).random()
}
