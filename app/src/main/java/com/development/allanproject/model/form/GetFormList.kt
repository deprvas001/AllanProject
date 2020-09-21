package com.development.allanproject.model.form

data class GetFormList(
    val additional: String,
    val code: Int,
    val `data`: List<FormData>,
    val status: String,
    val success: Boolean
)