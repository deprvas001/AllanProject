package com.development.allanproject.model.ehrs

data class EHRSData(
    val created_at: String,
    val doc_type: Int,
    val doc_url: Any,
    val health_doc_id: Int,
    val id: Int,
    var name: String,
    val nurse_id: Int,
    val status: Boolean,
    val updated_at: String
)