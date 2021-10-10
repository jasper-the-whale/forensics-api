package com.forensics.api.processors

import com.forensics.api.config.Authentication
import com.forensics.api.model.InvalidEmail
import com.forensics.api.model.ValidEmail
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EmailValidatorTest {
    private val anValidEmail = "valid@email.co.uk"
    private val anAuth = Authentication(emails = listOf(anValidEmail))
    private val anEmailValidator = EmailValidator(authentication = anAuth)

    @Nested
    inner class validate {
        @Test
        fun `when provided email is in auth email list, return ValidEmail`() {
            val actual = anEmailValidator.validate(anValidEmail)
            val expected = ValidEmail

            assertEquals(expected, actual)
        }

        @Test
        fun `when provided email is not in auth email list, return InvalidEmail`() {
            val anInvalidEmail = "rubbish@email.co.uk"
            val actual = anEmailValidator.validate(anInvalidEmail)
            val expected = InvalidEmail

            assertEquals(expected, actual)
        }
    }
}
