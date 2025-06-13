package com.mindera.presentation.ui.composes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.mindera.designsystem.FilterAppBar
import com.mindera.presentation.models.CompanyUi
import com.mindera.presentation.models.LaunchUi
import com.mindera.presentation.models.Link
import com.mindera.presentation.ui.applyLaunchFilters
import com.mindera.presentation.ui.composes.filter.LaunchFilterBottomSheet
import com.mindera.presentation.ui.composes.home.CompanyCard
import com.mindera.presentation.ui.composes.home.LaunchOptionsDialog
import com.mindera.presentation.ui.composes.home.LaunchesList
import com.mindera.presentation.ui.handleOpenUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeCompose(
    company: CompanyUi?,
    launches: List<LaunchUi>?,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedArticleUrl by remember { mutableStateOf("") }
    var selectedWikipediaUrl by remember { mutableStateOf("") }
    var selectedVideoUrl by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    val allLaunches = launches.orEmpty()

    var selectedYears by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var isSuccessOnly by remember { mutableStateOf(false) }
    var isAscending by remember { mutableStateOf(false) }
    var filteredLaunches by remember { mutableStateOf(allLaunches) }



    Column {
        FilterAppBar("Space X") {
            showSheet = true
        }
        company?.apply {
            CompanyCard(
                companyName = name,
                founderName = founderName,
                year = foundedYear,
                employees = employeesCount,
                launchSites = launchSitesCount,
                valuation = valuation
            )
        }
        launches?.let {
            LaunchesList(launchesList = filteredLaunches) { wiki, video, article ->
                selectedArticleUrl = article
                selectedVideoUrl = video
                selectedWikipediaUrl = wiki

                showDialog = true
            }
        }

    }
    LaunchOptionsDialog(
        show = showDialog,
        onDismiss = { showDialog = false }
    ) { link ->
        when (link) {
            Link.ARTICLE -> handleOpenUrl(context, selectedArticleUrl)
            Link.WIKIPEDIA -> handleOpenUrl(context, selectedWikipediaUrl)
            Link.YOUTUBE -> handleOpenUrl(context, selectedVideoUrl)
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            LaunchFilterBottomSheet(
                allYears = launches?.mapNotNull { it.year }?.distinct() ?: listOf(),
                selectedYears = selectedYears,
                isSuccessOnly = isSuccessOnly,
                isAscending = isAscending,
                onApply = { filter ->
                    selectedYears = filter.years
                    isSuccessOnly = filter.launchSuccess
                    isAscending = filter.isAscending

                    filteredLaunches = applyLaunchFilters(
                        allLaunches,
                        filter.years,
                        filter.launchSuccess,
                        filter.isAscending
                    )
                    showSheet = false
                },
                onDismiss = {
                    showSheet = false
                }
            )
        }
    }
}