package com.development.allanproject.model.adddocumentModel

data class GetDocumentSpinner(
    val additional: List<String>,
    val code: Int,
    val `data`: ArrayList<MyDocumentData>,
    val status: String,
    val success: Boolean
)