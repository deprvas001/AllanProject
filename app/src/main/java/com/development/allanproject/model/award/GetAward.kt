package com.development.allanproject.model.award

data class GetAward(
    val additional: String,
    val code: Int,
    val `data`: List<AwardData>,
    val status: String,
    val success: Boolean
)