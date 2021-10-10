package com.forensics.api.processors

import com.forensics.api.model.IncorrectLocation
import com.forensics.api.model.Location
import com.forensics.api.model.NoRemainingGuesses
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested

class LocationValidatorTest {
    private val locationValidator = LocationValidator()
    private val finalLocation = Location(2, 3)

    @Nested
    inner class validate {
        @Test
        fun `when guesses equals zero, returns NoRemainingGuesses`() {
            val actual = locationValidator.validate(
                x = 1, y = 2, guesses = 0, finalLocation = finalLocation
            )

            assertEquals(NoRemainingGuesses, actual)
        }

        @Test
        fun `when x-y match finalLocation and guesses is not zero, returns CorrectLocation`() {
            val actual = locationValidator.validate(
                x = 2, y = 3, guesses = 4, finalLocation = finalLocation
            )

            assertEquals(NoRemainingGuesses, actual)
        }

        @Test
        fun `when x-y don't match finalLocationand guesses is not zero, returns IncorrectLocation`() {
            val actual = locationValidator.validate(
                x = 1, y = 2, guesses = 4, finalLocation = finalLocation
            )

            assertEquals(IncorrectLocation, actual)
        }
    }
}
