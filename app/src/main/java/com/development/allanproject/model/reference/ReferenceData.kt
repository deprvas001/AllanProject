package com.development.allanproject.model.reference

data class ReferenceData(
    val created_at: String,
    val email: String,
    val experience_id: Int,
    val facility_id: Int,
    val facility_name: String,
    val id: Int,
    val job_title: String,
    val job_type: String,
    val name: String,
    val nurse_id: Int,
    val phone: String,
    val status: Boolean,
    val std_code: String,
    val updated_at: String
)