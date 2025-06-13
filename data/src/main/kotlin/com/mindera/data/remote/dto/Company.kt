package com.mindera.data.remote.dto

data class Company(
    val ceo: String?,
    val coo: String?,
    val cto: String?,
    val cto_propulsion: String?,
    val employees: Int?,
    val founded: Int?,
    val founder: String?,
    val headquarters: Headquarters?,
    val launch_sites: Int?,
    val links: LinksX?,
    val name: String?,
    val summary: String?,
    val test_sites: Int?,
    val valuation: Long?,
    val vehicles: Int?
)