package com.development.allanproject.model.speciality

data class GetSpeciality(
    val code: Int,
    val `data`: List<Data>,
    val status: String,
    val success: Boolean
)