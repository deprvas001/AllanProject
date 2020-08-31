package com.development.allanproject.model.commonapi

data class CommonApiData(
    val code: Int,
    val `data`: Data,
    val status: String,
    val success: Boolean
)