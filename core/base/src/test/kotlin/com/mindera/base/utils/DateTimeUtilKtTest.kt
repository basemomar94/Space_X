package com.mindera.base.utils

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Calendar
import java.util.Locale

class DateTimeUtilKtTest {

    @Test fun `isInFuture with future timestamp`() {
        val futureTimestamp = (System.currentTimeMillis() / 1000) + 1000
        assertTrue(futureTimestamp.isInFuture())
    }

    @Test fun `isInFuture with past timestamp`() {
        val pastTimestamp = (System.currentTimeMillis() / 1000) - 1000
        assertFalse(pastTimestamp.isInFuture())
    }

    @Test fun `isInFuture with current timestamp`() {
        val currentTimestamp = System.currentTimeMillis() / 1000
        assertFalse(currentTimestamp.isInFuture())
    }

    @Test fun `isInFuture with null timestamp`() {
        val nullTimestamp: Long? = null
        assertTrue(nullTimestamp.isInFuture())
    }

    @Test fun `isInFuture with timestamp exactly one second in the future`() {
        val futureTimestamp = (System.currentTimeMillis() / 1000) + 1
        assertTrue(futureTimestamp.isInFuture())
    }

    @Test fun `isInFuture with timestamp exactly one second in the past`() {
        val pastTimestamp = (System.currentTimeMillis() / 1000) - 1
        assertFalse(pastTimestamp.isInFuture())
    }

    @Test fun `isInFuture with Long MAX VALUE timestamp`() {
        val maxTimestamp = Long.MAX_VALUE
        assertTrue(maxTimestamp.isInFuture())
    }

    @Test fun `isInFuture with Long MIN VALUE timestamp`() {
        val minTimestamp = Long.MIN_VALUE
        assertFalse(minTimestamp.isInFuture())
    }

    @Test fun `isInFuture with zero timestamp`() {
        val zeroTimestamp: Long = 0
        assertFalse(zeroTimestamp.isInFuture())
    }

    // === getFormattedDate tests ===

    @Test fun `getFormattedDate with null timestamp`() {
        val timestamp: Long? = null
        assertEquals("N/A", timestamp.getFormattedDate())
    }

    @Test fun `getFormattedDate with zero timestamp`() {
        val timestamp = 0L
        assertTrue(timestamp.getFormattedDate().isNotEmpty())
    }

    @Test fun `getFormattedDate with a positive timestamp  current time `() {
        val timestamp = System.currentTimeMillis() / 1000
        assertTrue(timestamp.getFormattedDate().isNotEmpty())
    }

    @Test fun `getFormattedDate with a future timestamp`() {
        val timestamp = (System.currentTimeMillis() / 1000) + 86400
        assertTrue(timestamp.getFormattedDate().isNotEmpty())
    }

    @Test fun `getFormattedDate with a past timestamp`() {
        val timestamp = (System.currentTimeMillis() / 1000) - 86400
        assertTrue(timestamp.getFormattedDate().isNotEmpty())
    }

    @Test fun `getFormattedDate with Long MAX VALUE timestamp`() {
        val timestamp = Long.MAX_VALUE
        assertDoesNotThrow { timestamp.getFormattedDate() }
    }

    @Test fun `getFormattedDate with Long MIN VALUE timestamp`() {
        val timestamp = Long.MIN_VALUE
        assertDoesNotThrow { timestamp.getFormattedDate() }
    }

    @Test fun `getFormattedDate with different locales`() {
        val timestamp = System.currentTimeMillis() / 1000
        val original = Locale.getDefault()
        Locale.setDefault(Locale.FRANCE)
        val frDate = timestamp.getFormattedDate()
        Locale.setDefault(Locale.US)
        val usDate = timestamp.getFormattedDate()
        Locale.setDefault(original)
        assertNotEquals(frDate, usDate)
    }

    // === getFormattedTime tests ===

    @Test fun `getFormattedTime with null timestamp`() {
        val timestamp: Long? = null
        assertEquals("N/A", timestamp.getFormattedTime())
    }

    @Test fun `getFormattedTime with zero timestamp`() {
        val timestamp = 0L
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime with current time timestamp`() {
        val timestamp = System.currentTimeMillis() / 1000
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime with a future timestamp`() {
        val timestamp = (System.currentTimeMillis() / 1000) + 3600
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime with a past timestamp`() {
        val timestamp = (System.currentTimeMillis() / 1000) - 3600
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime with Long MAX VALUE timestamp`() {
        val timestamp = Long.MAX_VALUE
        assertDoesNotThrow { timestamp.getFormattedTime() }
    }

    @Test fun `getFormattedTime with Long MIN VALUE timestamp`() {
        val timestamp = Long.MIN_VALUE
        assertDoesNotThrow { timestamp.getFormattedTime() }
    }

    @Test fun `getFormattedTime with different locales`() {
        val timestamp = System.currentTimeMillis() / 1000
        val original = Locale.getDefault()
        Locale.setDefault(Locale.FRANCE)
        val frTime = timestamp.getFormattedTime()
        Locale.setDefault(Locale.US)
        val usTime = timestamp.getFormattedTime()
        Locale.setDefault(original)
        assertNotEquals(frTime, usTime)
    }

    @Test fun `getFormattedTime for timestamp at midnight`() {
        val timestamp = 0L
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime for timestamp at noon`() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val timestamp = calendar.timeInMillis / 1000
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    @Test fun `getFormattedTime for timestamp just before midnight`() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }
        val timestamp = calendar.timeInMillis / 1000
        assertTrue(timestamp.getFormattedTime().isNotEmpty())
    }

    // === getDaysDifference tests ===

    @Test fun `getDaysDifference with null timestamp`() {
        val timestamp: Long? = null
        assertEquals(0, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with current timestamp`() {
        val timestamp = System.currentTimeMillis() / 1000
        assertEquals(0, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with future timestamp  less than 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) + 86399
        assertEquals(0, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with past timestamp  less than 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) - 86399
        assertEquals(0, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with future timestamp  exactly 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) + 86400
        assertEquals(1, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with past timestamp  exactly 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) - 86400
        assertEquals(1, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with future timestamp  more than 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) + 172800
        assertEquals(2, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with past timestamp  more than 24 hours `() {
        val timestamp = (System.currentTimeMillis() / 1000) - 259200
        assertEquals(3, timestamp.getDaysDifference())
    }

    @Test fun `getDaysDifference with Long MAX VALUE timestamp`() {
        val timestamp = Long.MAX_VALUE
        assertTrue(timestamp.getDaysDifference() > 0)
    }

    @Test fun `getDaysDifference with Long MIN VALUE timestamp`() {
        val timestamp = Long.MIN_VALUE
        assertTrue(timestamp.getDaysDifference() > 0)
    }

    @Test fun `getDaysDifference with zero timestamp  epoch `() {
        val timestamp = 0L
        assertTrue(timestamp.getDaysDifference() > 0)
    }
}
