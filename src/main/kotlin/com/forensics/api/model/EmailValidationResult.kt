package com.forensics.api.model

sealed class EmailValidationResult

object InvalidEmail: EmailValidationResult()
object ValidEmail: EmailValidationResult()
