package com.mindera.base.utils

import android.util.Log
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class LoggerTest {


    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        mockkStatic(Log::class)
        logger = Logger("TestTag")
    }

    @AfterEach
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `d logs debug message`() {
        logger.d("Hello")
        verify { Log.d("TestTag", "Hello") }
    }

    @Test
    fun `i logs debug message`() {
        logger.i("Hello")
        verify { Log.i("TestTag", "Hello") }
    }

    @Test
    fun `e logs debug message`() {
        logger.e("Hello")
        verify { Log.e("TestTag", "Hello") }
    }

    @Test
    fun `e message throwable logs correctly`() {
        val throwable = RuntimeException("Something went wrong")

        logger.e("Oops", throwable)

        verify { Log.e("TestTag", "Oops", throwable) }
    }

    @Test
    fun `Constructor with empty tag`() {
        val logger = Logger("")
        logger.d("empty")
        verify { Log.d("", "empty") }
    }

}