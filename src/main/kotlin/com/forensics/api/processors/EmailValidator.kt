package com.forensics.api.processors


import com.forensics.api.config.Authentication
import com.forensics.api.model.EmailValidationResult
import com.forensics.api.model.InvalidEmail
import com.forensics.api.model.ValidEmail
import org.springframework.stereotype.Component

@Component
class EmailValidator(
    private val authentication: Authentication = Authentication()
) {
    fun validate(email: String): EmailValidationResult {
        return try {
            authentication.emails.first { it == email }

            ValidEmail
        } catch (e: NoSuchElementException) {
            InvalidEmail
        }
    }
}
