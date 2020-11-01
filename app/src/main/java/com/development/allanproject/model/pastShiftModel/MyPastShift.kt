package com.development.allanproject.model.pastShiftModel

data class MyPastShift(
    val auth_token: String,
    val code: Int,
    val shift: PastShiftDetail,
    val status: String,
    val success: Boolean,
    val user_id: Int
)