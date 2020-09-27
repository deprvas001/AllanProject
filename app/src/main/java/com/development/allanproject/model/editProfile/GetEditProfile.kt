package com.development.allanproject.model.editProfile

data class GetEditProfile(
    val code: Int,
    val `data`: List<EditData>,
    val optional: List<Any>,
    val status: String,
    val success: Boolean
)