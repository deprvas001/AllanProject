package com.development.allanproject.model.facilityprofileModel

data class GetFacilityProfile(
    val auth_token: String,
    val code: Int,
    val facility: FacilityProfileData,
    val status: String,
    val success: Boolean,
    val user_id: Int
)