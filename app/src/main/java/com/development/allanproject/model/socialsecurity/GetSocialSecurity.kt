package com.development.allanproject.model.socialsecurity

data class GetSocialSecurity(
    val additional: String,
    val code: Int,
    val `data`: List<SecurityData>,
    val status: String,
    val success: Boolean
)