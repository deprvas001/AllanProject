package com.development.allanproject.model.award

data class AwardData(
    val award: String,
    val award_date: String,
    val created_at: String,
    val id: Int,
    val nurse_id: Int,
    val organization: String,
    val status: Boolean,
    val updated_at: String
)