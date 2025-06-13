package com.mindera.presentation.models

data class Filter(
    val years: Set<Int>,
    val launchSuccess: Boolean,
    val isAscending: Boolean,
)