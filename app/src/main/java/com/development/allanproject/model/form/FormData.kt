package com.development.allanproject.model.form

data class FormData(
    val form_id: Int,
    val form_url_upload: String,
    val form_url_view: String,
    val icon: String,
    val id: Int,
    val name: String,
    val upload_status: String
)