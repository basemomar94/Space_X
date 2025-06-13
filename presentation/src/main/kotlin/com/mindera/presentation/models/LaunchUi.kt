package com.mindera.presentation.models

import androidx.compose.runtime.Stable

@Stable
data class LaunchUi(
    val missionName: String,
    val date: String,
    val time: String,
    val rocketName: String,
    val daysSinceLaunch: Long,
    val videoUrl: String,
    val articleUrl: String,
    val wikipediaLink: String,
    val isSuccess: Boolean,
    val image: String,
    val isInFuture: Boolean,
    val year: Int,
    val launchUnix: Long?
)