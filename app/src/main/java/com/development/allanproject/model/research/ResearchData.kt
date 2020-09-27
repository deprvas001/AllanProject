package com.development.allanproject.model.research

data class ResearchData(
    val address: String,
    val created_at: String,
    val end_date: String,
    val id: Int,
    val nurse_id: Int,
    val organization: String,
    val start_date: String,
    val status: Boolean,
    val updated_at: String
)