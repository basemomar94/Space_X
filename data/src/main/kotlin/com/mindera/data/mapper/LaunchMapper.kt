package com.mindera.data.mapper

import com.mindera.base.BaseMapper
import javax.inject.Inject
import com.mindera.domain.models.Launch as LaunchModel
import com.mindera.data.remote.dto.Launch as LaunchDto

class LaunchMapper @Inject constructor() : BaseMapper<LaunchDto, LaunchModel> {

    override fun map(data: LaunchDto): LaunchModel {
        return LaunchModel(
            launchUnix = data.launch_date_unix,
            missionName = data.mission_name,
            rocketName = data.rocket?.rocket_name ,
            videoUrl = data.links?.video_link ,
            wikipediaLink = data.links?.wikipedia ,
            isSuccess = data.launch_success ,
            image = data.links?.mission_patch,
            articleUrl = data.links?.article_link
        )
    }

}