package com.mindera.base.utils

import android.content.Context
import com.mindera.base.models.ErrorTypes
import com.mindera.rocketscience.base.R
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetErrorMessageTest {

    private val context = mockk<Context>()

    @BeforeEach
    fun setUp() {
        every { context.getString(R.string.net_work_error) } returns "No internet connection"
        every { context.getString(R.string.unexpected_error) } returns "Unexpected Error"
        every { context.getString(R.string.bad_request) } returns "Bad request. Please check your input."
        every { context.getString(R.string.unauthorized) } returns "You need to log in again."
        every { context.getString(R.string.forbidden) } returns "Access denied."
        every { context.getString(R.string.not_found) } returns "Item not found."
        every { context.getString(R.string.server_error) } returns "Server error. Please try later."
        every { context.getString(R.string.http_error_generic, any()) } answers {
            val code = secondArg<Int>()
            "Unexpected error ($code)"
        }
    }

    @Test
    fun `returns network error for IoException`() {
        val result = context.getErrorMessage(ErrorTypes.IoException)
        assertEquals("No internet connection", result)
    }

    @Test
    fun `returns bad request for 400`() {
        val result = context.getErrorMessage(ErrorTypes.Http(400, null))
        assertEquals("Bad request. Please check your input.", result)
    }

    @Test
    fun `returns unauthorized for 401`() {
        val result = context.getErrorMessage(ErrorTypes.Http(401, null))
        assertEquals("You need to log in again.", result)
    }

    @Test
    fun `returns forbidden for 403`() {
        val result = context.getErrorMessage(ErrorTypes.Http(403, null))
        assertEquals("Access denied.", result)
    }

    @Test
    fun `returns not found for 404`() {
        val result = context.getErrorMessage(ErrorTypes.Http(404, null))
        assertEquals("Item not found.", result)
    }

    @Test
    fun `returns server error for 500`() {
        val result = context.getErrorMessage(ErrorTypes.Http(500, null))
        assertEquals("Server error. Please try later.", result)
    }

    @Test
    fun `returns generic http error for unknown code`() {
        every { context.getString(R.string.http_error_generic, *anyVararg()) } answers {
            val code = (secondArg<Array<*>>())[0] as Int
            "Unexpected error ($code)"
        }

        val result = context.getErrorMessage(ErrorTypes.Http(666, ""))
        assertEquals("Unexpected error (666)", result)
    }

    @Test
    fun `returns reason if provided in Unknown`() {
        val result = context.getErrorMessage(ErrorTypes.Unknown("Boom"))
        assertEquals("Boom", result)
    }

    @Test
    fun `returns unexpected error if Unknown has null reason`() {
        val result = context.getErrorMessage(ErrorTypes.Unknown(null))
        assertEquals("Unexpected Error", result)
    }
}
