package com.development.allanproject.model.myprofile

data class GetMyProfile(
    val additional: String,
    val code: Int,
    val `data`: ProfileData,
    val status: String,
    val success: Boolean
)