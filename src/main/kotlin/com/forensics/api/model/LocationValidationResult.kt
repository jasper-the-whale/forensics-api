package com.forensics.api.model

sealed class LocationValidationResult

object IncorrectLocation : LocationValidationResult()
object CorrectLocation : LocationValidationResult()
object NoRemainingGuesses: LocationValidationResult()
