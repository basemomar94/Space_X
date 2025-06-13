package com.mindera.data.mapper

import com.mindera.data.remote.dto.Links
import com.mindera.data.remote.dto.Rocket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.mindera.data.remote.dto.Launch as LaunchDto

class LaunchMapperTest {

    private val mapper = LaunchMapper()

    @Test
    fun `map should correctly convert CompanyDto to CompanyModel`() {
        val dto = LaunchDto(
            details = "Good company",
            flight_number = 1,
            is_tentative = false,
            launch_date_local = "2023-01-01T12:00:00",
            launch_date_unix = 1672531200,
            launch_date_utc = "2023-01-01T12:00:00",
            launch_failure_details = null,
            launch_site = null,
            launch_success = true,
            launch_window = 0,
            launch_year = "2023",
            links = Links(
                video_link = "YouTube",
                wikipedia = "Wikipedia",
                article_link = "article",
                flickr_images = emptyList(),
                mission_patch = "image",
                mission_patch_small = null,
                presskit = null,
                reddit_campaign = null,
                reddit_launch = null,
                reddit_media = null,
                reddit_recovery = null,
                youtube_id = null

            ),
            mission_id = emptyList(),
            mission_name = "SpaceX",
            rocket = Rocket(
                rocket_id = "1",
                rocket_name = "XOne",
                rocket_type = "Falcon",
                first_stage = null,
                second_stage = null,
                fairings = null
            ),
            ships = emptyList(),
            static_fire_date_unix = null,
            static_fire_date_utc = null,
            tbd = false,
            telemetry = null,
            tentative_max_precision = "hour",
            timeline = null,
            upcoming = false,
            crew = null,
        )

        val model = mapper.map(dto)

        assertEquals("SpaceX", model.missionName)
        assertEquals(1672531200, model.launchUnix)
        assertEquals(true, model.isSuccess)
        assertEquals("image", model.image)
        assertEquals("YouTube", model.videoUrl)
        assertEquals("article", model.articleUrl)
        assertEquals("Wikipedia", model.wikipediaLink)
        assertEquals("XOne", model.rocketName)
    }

}