package com.development.allanproject.model.backinformation

data class GetBackgroundData(
    val additional: String,
    val code: Int,
    val `data`: List<BackgroundData>,
    val status: String,
    val success: Boolean
)