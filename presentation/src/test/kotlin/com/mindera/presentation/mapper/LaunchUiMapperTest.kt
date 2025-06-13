package com.mindera.presentation.mapper

import com.mindera.base.utils.getDaysDifference
import com.mindera.base.utils.getFormattedDate
import com.mindera.base.utils.getFormattedTime
import com.mindera.base.utils.isInFuture
import com.mindera.domain.models.Launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class LaunchUiMapperTest {

    private val mapper = LaunchUiMapper()

    @Test
    fun `maps all non-null fields correctly`() {
        val launch = Launch(
            missionName = "Falcon 9",
            rocketName = "F9 Block 5",
            launchUnix = 1234567890L,
            videoUrl = "https://example.com/video",
            articleUrl = "https://example.com/article",
            wikipediaLink = "https://example.com/wiki",
            isSuccess = true,
            image = "https://example.com/image.jpg"
        )

        val result = mapper.map(launch)

        assertEquals("Falcon 9", result.missionName)
        assertEquals(1234567890L.getFormattedDate(), result.date)
        assertEquals(1234567890L.getFormattedTime(), result.time)
        assertEquals("F9 Block 5", result.rocketName)
        assertEquals(1234567890L.getDaysDifference(), result.daysSinceLaunch)
        assertEquals("https://example.com/video", result.videoUrl)
        assertEquals("https://example.com/article", result.articleUrl)
        assertEquals("https://example.com/wiki", result.wikipediaLink)
        assertEquals(true, result.isSuccess)
        assertEquals("https://example.com/image.jpg", result.image)
        assertEquals(1234567890L.isInFuture(), result.isInFuture)
    }

    @Test
    fun `maps null fields to default values`() {
        val launch = Launch(
            missionName = null,
            rocketName = null,
            launchUnix = 1234567890L,
            videoUrl = null,
            articleUrl = null,
            wikipediaLink = null,
            isSuccess = null,
            image = null
        )

        val result = mapper.map(launch)

        assertEquals("Unknown", result.missionName)
        assertEquals("Unknown", result.rocketName)
        assertEquals("", result.videoUrl)
        assertEquals("", result.articleUrl)
        assertEquals("", result.wikipediaLink)
        assertEquals("", result.image)
        assertFalse(result.isSuccess)
    }
}
