package com.forensics.api.processors

import com.forensics.api.model.Direction
import com.forensics.api.model.EvidenceData
import com.forensics.api.model.Location
import com.forensics.api.model.Movement
import com.forensics.api.model.TravelData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested

internal class EvidenceGeneratorTest {
    private val someTravelData = TravelData(movements = listOf(Movement(Direction.EAST, 2)))
    private val aFinalLocation = Location(1, 2)

    @Nested
    inner class newEvidence {
        @Test
        fun `should return some EvidenceData`() {
            val mockTravelDataGenerator = mockk<TravelDataGenerator>()
            val mockTravelDataProcessor = mockk<TravelDataProcessor>()

            every { mockTravelDataGenerator.newTravelData() } returns someTravelData
            every { mockTravelDataProcessor.getFinalLocation(any()) } returns aFinalLocation

            val actual = EvidenceGenerator(mockTravelDataGenerator, mockTravelDataProcessor).newEvidence()
            val expected = EvidenceData(someTravelData, aFinalLocation)

            assertEquals(expected, actual)
        }
    }
}
