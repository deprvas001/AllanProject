package com.development.allanproject.model.i9form

data class GetI9Form(
    val additional: String,
    val code: Int,
    val `data`: I9FormData,
    val status: String,
    val success: Boolean
)