package com.development.allanproject.model.certificate

data class Data(
    val created_at: String,
    val exp_years: String,
    val id: Int,
    val nurse_id: Int,
    val speciality_id: Int,
    val status: Boolean,
    val updated_at: String
)