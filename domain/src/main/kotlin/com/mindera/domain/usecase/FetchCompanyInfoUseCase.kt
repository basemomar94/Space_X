package com.mindera.domain.usecase

import com.mindera.domain.repo.CompanyRepo
import javax.inject.Inject

class FetchCompanyInfoUseCase @Inject constructor (private val companyRepo: CompanyRepo) {

    operator fun invoke() = companyRepo.getCompanyInfo()
}