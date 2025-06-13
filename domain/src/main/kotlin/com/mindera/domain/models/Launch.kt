package com.mindera.domain.models

data class Launch(
    val missionName: String?,
    val launchUnix: Long?,
    val rocketName: String?,
    val videoUrl: String?,
    val articleUrl: String?,
    val wikipediaLink: String?,
    val isSuccess: Boolean?,
    val image: String?,
)
