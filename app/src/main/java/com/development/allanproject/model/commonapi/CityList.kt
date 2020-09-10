package com.development.allanproject.model.commonapi

data class CityList(
    val code: Int,
    val `cities`: ArrayList<String>,
    val status: String,
    val success: Boolean
)