package com.forensics.api.processors

import com.forensics.api.model.Location
import com.forensics.api.model.Movement
import com.forensics.api.model.TravelData
import org.springframework.stereotype.Component

@Component
class TravelDataProcessor {

    fun getFinalLocation(travelData: TravelData): Location {
        val startLocation = travelData.startLocation
        val locations = travelData.movements.map { it.toLocation() }

        val xFinalLocation = locations.map { it.x }.fold(startLocation.x) { total, location -> total + location }
        val yFinalLocation = locations.map { it.y }.fold(startLocation.y) { total, location -> total + location }

        return Location(xFinalLocation, yFinalLocation)
    }

    private fun Movement.toLocation(): Location {
        val x = this.distance * this.direction.xDirectionFactor
        val y = this.distance * this.direction.yDirectionFactor
        return Location(x, y)
    }
}
