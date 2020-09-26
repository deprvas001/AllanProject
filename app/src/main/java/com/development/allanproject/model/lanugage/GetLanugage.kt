package com.development.allanproject.model.lanugage

data class GetLanugage(
    val additional: Additional,
    val code: Int,
    val `data`: ArrayList<LanguageData>,
    val status: String,
    val success: Boolean
)