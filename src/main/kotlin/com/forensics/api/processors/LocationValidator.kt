package com.forensics.api.processors

import com.forensics.api.model.CorrectLocation
import com.forensics.api.model.IncorrectLocation
import com.forensics.api.model.Location
import com.forensics.api.model.LocationValidationResult
import com.forensics.api.model.NoRemainingGuesses
import org.springframework.stereotype.Component

@Component
class LocationValidator {

    fun validate(x: Int, y: Int, guesses: Int, finalLocation: Location): LocationValidationResult {
        return if (guesses == 0) {
            NoRemainingGuesses
        } else if (x == finalLocation.x && y == finalLocation.y) {
            CorrectLocation
        } else {
            IncorrectLocation
        }
    }
}
