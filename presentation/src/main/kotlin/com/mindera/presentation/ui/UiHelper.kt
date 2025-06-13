package com.mindera.presentation.ui

import android.content.Context
import android.widget.Toast
import com.mindera.base.utils.openUrl
import com.mindera.presentation.R
import com.mindera.presentation.models.LaunchUi

fun handleOpenUrl(context: Context, url: String) {
    if (url.isEmpty()) {
        Toast.makeText(context, context.getString(R.string.no_url), Toast.LENGTH_SHORT).show()
    } else {
        openUrl(context, url)
    }
}

fun applyLaunchFilters(
    all: List<LaunchUi>,
    years: Set<Int>,
    successOnly: Boolean,
    asc: Boolean
): List<LaunchUi> {
    return all
        .filter { if (years.isEmpty()) true else years.contains(it.year) }
        .filter { if (successOnly) it.isSuccess else true }
        .sortedWith(compareBy<LaunchUi> { it.launchUnix ?: 0L }
            .let { if (asc) it else it.reversed() })
}