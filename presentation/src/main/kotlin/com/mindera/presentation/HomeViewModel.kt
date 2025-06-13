package com.mindera.presentation

import com.mindera.base.BaseViewModel
import com.mindera.base.models.ErrorTypes
import com.mindera.base.models.NetworkResult
import com.mindera.domain.usecase.FetchCompanyInfoUseCase
import com.mindera.domain.usecase.FetchLaunchesUseCase
import com.mindera.presentation.mapper.CompanyUiMapper
import com.mindera.presentation.mapper.LaunchUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCompanyInfoUseCase: FetchCompanyInfoUseCase,
    private val fetchLaunchesUseCase: FetchLaunchesUseCase,
    private val companyUiMapper: CompanyUiMapper,
    private val launchUiMapper: LaunchUiMapper,
) :
    BaseViewModel<HomeState, HomeIntent, HomeEffect>() {

    init {
        loadData()
    }

    override fun setInitialState(): HomeState {
        return HomeState.Loading
    }

    override suspend fun handleEvents(event: HomeIntent) {
        when (event) {
            HomeIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
        launchAndCollectResult(
            tag = "load-data",
            flow = combine(
                fetchCompanyInfoUseCase(),
                fetchLaunchesUseCase()
            ) { companyResult, launchesResult ->
                val companyData = (companyResult as? NetworkResult.Success)?.data
                val launchesData = (launchesResult as? NetworkResult.Success)?.data
                if (companyData == null || launchesData == null) {
                    val error = (companyResult as? NetworkResult.Failure)?.errorTypes
                        ?: (launchesResult as? NetworkResult.Failure)?.errorTypes
                        ?: ErrorTypes.Unknown(null)
                    setEffect { HomeEffect.Error(error) }
                    Pair(companyData, launchesData)
                } else {
                    Pair(companyData, launchesData)

                }
            },
            resultSuccess = { (company, launches) ->
                if (company == null || launches == null) {
                    setEffect { HomeEffect.Error(ErrorTypes.Unknown(null)) }
                } else {
                    setState {
                        HomeState.Success(
                            companyUiMapper.map(company),
                            launches.map { launchUiMapper.map(it) })
                    }
                }
            },
            onError = {
                setEffect { HomeEffect.Error(ErrorTypes.Unknown(null)) }
            },
            resultFailure = {
                setEffect { HomeEffect.Error(it.errorTypes) }
            },
        )
    }
}