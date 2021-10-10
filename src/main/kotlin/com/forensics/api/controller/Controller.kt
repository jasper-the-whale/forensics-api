package com.forensics.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.forensics.api.model.CorrectLocation
import com.forensics.api.model.IncorrectLocation
import com.forensics.api.model.InvalidEmail
import com.forensics.api.model.NoRemainingGuesses
import com.forensics.api.model.ValidEmail
import com.forensics.api.processors.EmailValidator
import com.forensics.api.processors.LocationValidator
import com.forensics.api.processors.EvidenceGenerator
import mu.KLogger
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    private val emailValidator: EmailValidator = EmailValidator(),
    private val evidenceGenerator: EvidenceGenerator = EvidenceGenerator(),
    private val locationValidator: LocationValidator = LocationValidator(),
    private val logger: KLogger = KotlinLogging.logger {}
) {
    var remainingGuesses = 5

    @GetMapping("api/{email}/directions")
    @ResponseBody
    fun directions(
        @PathVariable(value = "email") email: String,
    ): ResponseEntity<String> {
        logger.info { "Request for directions endpoint" }
        return when (emailValidator.validate(email)) {
            is InvalidEmail -> {
                logger.warn { "Invalid email passed as parameter" }
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email provided unauthorized")
            }
            is ValidEmail -> {
                val directions = evidenceGenerator.evidence.travelData
                val response = jacksonObjectMapper().writeValueAsString(directions)
                logger.info { "Successful request for directions." }

                ResponseEntity.status(HttpStatus.OK).body(response)
            }
        }
    }

    @GetMapping("api/{email}/location/{x}/{y}")
    @ResponseBody
    fun location(
        @PathVariable(value = "email") email: String,
        @PathVariable(value = "x") x: Int,
        @PathVariable(value = "y") y: Int
    ): ResponseEntity<String> {
        logger.info { "Request for location endpoint" }
        return when (emailValidator.validate(email)) {
            is InvalidEmail -> {
                logger.warn { "Invalid email passed as parameter" }
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email provided unauthorized")
            }
            is ValidEmail -> {
                val finalLocation = evidenceGenerator.evidence.finalLocation
                val result = locationValidator.validate(x, y, remainingGuesses, finalLocation)
                when (result) {
                    is NoRemainingGuesses ->{
                        logger.warn { "No remaining guess to guess the cats location" }
                        ResponseEntity.status(HttpStatus.FORBIDDEN).body("The witch got away!")
                    }
                    is CorrectLocation -> {
                        logger.info { "Successful guess for location the cats" }
                        ResponseEntity.status(HttpStatus.OK).body("Correct location, you have saved the cats!")
                    }
                    is IncorrectLocation -> {
                        logger.warn { "Incorrect guess made of cats locations" }
                        remainingGuesses -= 1
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Incorrect location, you have $remainingGuesses guesses remaining")
                    }
                }
            }
        }
    }
}
