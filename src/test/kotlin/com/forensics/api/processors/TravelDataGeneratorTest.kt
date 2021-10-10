package com.forensics.api.processors

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TravelDataGeneratorTest {
    @Nested
    inner class newTravelData {
        @Test
        fun `should return valid TravelData`() {
            val actual = TravelDataGenerator().newTravelData()

            // Should have a total of movements in range to 10
            assertTrue(actual.movements.size in 5..10)

            // each movement should distance between 1 and 5
            actual.movements.forEach {
                assertTrue(it.distance in 1..5)
            }
        }
    }
}
