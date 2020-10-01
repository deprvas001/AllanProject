package com.development.allanproject.model.preferenceModel

data class GetPreferenceList(
    val additional: String,
    val code: Int,
    val `data`: PreferenceData,
    val status: String,
    val success: Boolean
)