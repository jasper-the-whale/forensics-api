package com.forensics.api.processors

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.forensics.api.model.Location
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import com.forensics.api.model.TravelData
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.IOException

class TravelDataProcessorTest {

    private val travelDataString = File("./data/exampleTravelData.json").readText(Charsets.UTF_8)
    private val travelData = deserialize(travelDataString, TravelData::class.java)

    @Nested
    inner class getFinalLocation {
        @Test
        fun `provided valid TravelData, returns final location of directions`() {
            val actual = TravelDataProcessor().getFinalLocation(travelData)
            val expected = Location(x = 1, y = 2)

            assertEquals(expected, actual)
        }
    }

    @Throws(IOException::class)
    fun <T> deserialize(json: String, deserializeTo: Class<T>): T =
        jacksonObjectMapper().readValue(json, deserializeTo)
}
