package com.development.allanproject.model.research

data class GetResearch(
    val additional: String,
    val code: Int,
    val `data`: List<ResearchData>,
    val status: String,
    val success: Boolean
)