package com.development.allanproject.model.preferedfacility

data class GetPreferedFacility(
    val code: Int,
    val facilities: List<FacilityData>,
    val status: String,
    val success: Boolean
)