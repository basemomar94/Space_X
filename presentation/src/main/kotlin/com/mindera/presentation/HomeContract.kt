package com.mindera.presentation

import com.mindera.base.ViewIntent
import com.mindera.base.ViewSideEffect
import com.mindera.base.ViewState
import com.mindera.base.models.ErrorTypes
import com.mindera.presentation.models.CompanyUi
import com.mindera.presentation.models.LaunchUi

sealed class HomeState : ViewState {
    data object Loading : HomeState()
    data class Success(val companyUi: CompanyUi?, val launch: List<LaunchUi>?) : HomeState()
}

sealed class HomeIntent : ViewIntent {
    data object LoadData : HomeIntent()
}

sealed class HomeEffect : ViewSideEffect {
    data class Error(val errorTypes: ErrorTypes) : HomeEffect()

}