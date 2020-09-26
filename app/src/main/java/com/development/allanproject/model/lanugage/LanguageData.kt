package com.development.allanproject.model.lanugage

data class LanguageData(
    val created_at: String,
    val id: Int,
    val language: String,
    val nurse_id: Int,
    val proficiency: String,
    val status: Boolean,
    val updated_at: String
)