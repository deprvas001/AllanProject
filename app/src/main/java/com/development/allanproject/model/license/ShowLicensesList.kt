package com.development.allanproject.model.license

data class ShowLicensesList(
    val addtional: List<Any>,
    val code: Int,
    val `data`: List<Data>,
    val status: String,
    val success: Boolean
)