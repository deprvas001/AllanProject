package com.development.allanproject.model.hiddenjobs

data class GetHiddenShift(
    val address: String,
    val icon: String,
    val id: Int,
    val name: String,
    val price: String,
    val start_date: String,
    val type: String,
    val type_icon: String
)