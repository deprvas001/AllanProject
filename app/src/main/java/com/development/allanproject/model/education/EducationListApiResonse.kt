package com.development.allanproject.model.education

data class EducationListApiResonse(
    val addtional: List<Any>,
    val code: Int,
    val `data`: List<Data>,
    val status: String,
    val success: Boolean
)