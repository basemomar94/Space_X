package com.mindera.presentation.mapper

import com.mindera.base.BaseMapper
import com.mindera.base.utils.getDaysDifference
import com.mindera.base.utils.getFormattedDate
import com.mindera.base.utils.getFormattedTime
import com.mindera.base.utils.getYear
import com.mindera.base.utils.isInFuture
import com.mindera.domain.models.Launch
import com.mindera.presentation.models.LaunchUi
import javax.inject.Inject

class LaunchUiMapper @Inject constructor() : BaseMapper<Launch, LaunchUi> {

    override fun map(data: Launch): LaunchUi {
        return LaunchUi(
            missionName = data.missionName?:"Unknown",
            date = data.launchUnix.getFormattedDate(),
            time = data.launchUnix.getFormattedTime(),
            rocketName = data.rocketName?:"Unknown",
            daysSinceLaunch = data.launchUnix.getDaysDifference(),
            videoUrl = data.videoUrl.orEmpty(),
            wikipediaLink = data.wikipediaLink.orEmpty(),
            articleUrl = data.articleUrl.orEmpty(),
            isSuccess = data.isSuccess==true,
            image = data.image.orEmpty(),
            isInFuture = data.launchUnix.isInFuture(),
            year = data.launchUnix.getYear(),
            launchUnix = data.launchUnix

        )
    }
}