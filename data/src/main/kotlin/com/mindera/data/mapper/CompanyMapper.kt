package com.mindera.data.mapper
import com.mindera.base.BaseMapper
import javax.inject.Inject
import com.mindera.domain.models.Company as CompanyModel
import com.mindera.data.remote.dto.Company as CompanyDto

class CompanyMapper @Inject constructor(): BaseMapper<CompanyDto, CompanyModel> {

    override fun map(data: CompanyDto): CompanyModel {
        return CompanyModel(
            name = data.name,
            founderName = data.founder,
            foundedYear = data.founded ,
            employeesCount = data.employees,
            launchSitesCount = data.launch_sites,
            valuation = data.valuation,
        )
    }
}