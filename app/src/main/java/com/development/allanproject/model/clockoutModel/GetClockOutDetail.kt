package com.development.allanproject.model.clockoutModel

data class GetClockOutDetail(
    val auth_token: String,
    val code: Int,
    val shift: ClockOutShift,
    val status: String,
    val success: Boolean,
    val user_id: Int
)