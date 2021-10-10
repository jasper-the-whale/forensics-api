package com.forensics.api.controller

import com.forensics.api.model.CorrectLocation
import com.forensics.api.model.Direction
import com.forensics.api.model.EvidenceData
import com.forensics.api.model.IncorrectLocation
import com.forensics.api.model.InvalidEmail
import com.forensics.api.model.Location
import com.forensics.api.model.Movement
import com.forensics.api.model.NoRemainingGuesses
import com.forensics.api.model.TravelData
import com.forensics.api.model.ValidEmail
import com.forensics.api.processors.EmailValidator
import com.forensics.api.processors.EvidenceGenerator
import com.forensics.api.processors.LocationValidator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ControllerTest {
    private val mockEmailValidator = mockk<EmailValidator>()
    private val mockEvidenceGenerator = mockk<EvidenceGenerator>()
    private val mockLocationValidator = mockk<LocationValidator>()

    private val anEmail = "email@email.com"
    private val someEvidenceData = EvidenceData(
        travelData = TravelData(movements = listOf(Movement(Direction.EAST, 2))),
        finalLocation = Location(2, 0)
    )

    @Nested
    inner class directions {
        @Test
        fun `when invalid email provided, returns ResponseEntity with UNAUTHORIZED status code`() {
            every { mockEmailValidator.validate(any()) } returns InvalidEmail

            val actual = Controller(mockEmailValidator).directions(anEmail)

            assertEquals(HttpStatus.UNAUTHORIZED, actual.statusCode)
        }

        @Test
        fun `when valid email provided, returns ResponseEntity with OK status code with Travel Data in body`() {
            every { mockEmailValidator.validate(any()) } returns ValidEmail
            every { mockEvidenceGenerator.newEvidence() } returns someEvidenceData

            val actual = Controller(mockEmailValidator, mockEvidenceGenerator).directions(anEmail)

            assertEquals(HttpStatus.OK, actual.statusCode)
        }
    }

    @Nested
    inner class location {
        @Test
        fun `when invalid email provided, returns ResponseEntity with UNAUTHORIZED status code`() {
            every { mockEmailValidator.validate(any()) } returns InvalidEmail

            val actual = Controller(mockEmailValidator).location(anEmail, 0, 0)

            assertEquals(HttpStatus.UNAUTHORIZED, actual.statusCode)
        }

        @Nested
        inner class `when a valid email is provided` {
            @Test
            fun `when locationValidator returns NoRemainingGuesses, returns ResponseEntity with FORBIDDEN status code`() {
                every { mockEmailValidator.validate(any()) } returns ValidEmail
                every { mockEvidenceGenerator.newEvidence() } returns someEvidenceData
                every { mockLocationValidator.validate(any(), any(), any(), any()) } returns NoRemainingGuesses

                val actual = Controller(mockEmailValidator, mockEvidenceGenerator, mockLocationValidator)
                    .location(anEmail, 0, 0)

                assertEquals(HttpStatus.FORBIDDEN, actual.statusCode)
            }

            @Test
            fun `when locationValidator returns CorrectLocation, returns ResponseEntity with OK status code`() {
                every { mockEmailValidator.validate(any()) } returns ValidEmail
                every { mockEvidenceGenerator.newEvidence() } returns someEvidenceData
                every { mockLocationValidator.validate(any(), any(), any(), any()) } returns CorrectLocation

                val actual = Controller(mockEmailValidator, mockEvidenceGenerator, mockLocationValidator)
                    .location(anEmail, 0, 0)

                assertEquals(HttpStatus.OK, actual.statusCode)
            }

            @Test
            fun `when locationValidator returns IncorrectLocation, returns ResponseEntity with BAD_REQUEST status code`() {
                every { mockEmailValidator.validate(any()) } returns ValidEmail
                every { mockEvidenceGenerator.newEvidence() } returns someEvidenceData
                every { mockLocationValidator.validate(any(), any(), any(), any()) } returns IncorrectLocation

                val actual = Controller(mockEmailValidator, mockEvidenceGenerator, mockLocationValidator)
                    .location(anEmail, 0, 0)

                assertEquals(HttpStatus.BAD_REQUEST, actual.statusCode)
            }
        }
    }
}
