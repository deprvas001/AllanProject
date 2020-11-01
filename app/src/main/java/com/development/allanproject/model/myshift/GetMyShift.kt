package com.development.allanproject.model.myshift

data class GetMyShift(
    val auth_token: String,
    val code: Int,
    val is_active: Boolean,
    val shifts: ArrayList<ShiftItem>,
    val status: String,
    val success: Boolean,
    val user_id: Int
)