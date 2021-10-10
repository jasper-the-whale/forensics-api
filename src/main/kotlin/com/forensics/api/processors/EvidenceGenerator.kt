package com.forensics.api.processors

import com.forensics.api.model.EvidenceData
import mu.KLogger
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class EvidenceGenerator(
    private val travelDataGenerator: TravelDataGenerator = TravelDataGenerator(),
    private val travelDataProcessor: TravelDataProcessor = TravelDataProcessor(),
    private val logger: KLogger = KotlinLogging.logger {}
) {
    val evidence = newEvidence()

    fun newEvidence(): EvidenceData {
        val travelData = travelDataGenerator.newTravelData()
        val finalLocation = travelDataProcessor.getFinalLocation(travelData)

        return EvidenceData(travelData, finalLocation).also {
            logger.debug { "New evidence data has been created: $it" }
        }
    }
}
