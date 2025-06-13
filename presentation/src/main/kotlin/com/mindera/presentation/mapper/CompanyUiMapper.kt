package com.mindera.presentation.mapper

import com.mindera.base.BaseMapper
import javax.inject.Inject
import com.mindera.domain.models.Company as CompanyModel
import com.mindera.presentation.models.CompanyUi as CompanyUi

class CompanyUiMapper @Inject constructor() : BaseMapper<CompanyModel, CompanyUi> {

    override fun map(data: CompanyModel): CompanyUi {
        return CompanyUi(
            name = data.name ?: DEFAULT_STRING,
            founderName = data.founderName ?: DEFAULT_STRING,
            foundedYear = data.foundedYear ?: DEFAULT_INT,
            employeesCount = data.employeesCount ?: DEFAULT_INT,
            launchSitesCount = data.launchSitesCount ?: DEFAULT_INT,
            valuation = data.valuation ?: 0L,
        )
    }

    companion object {
        private const val DEFAULT_STRING = "Unknown"
        private const val DEFAULT_INT = -1
    }
}