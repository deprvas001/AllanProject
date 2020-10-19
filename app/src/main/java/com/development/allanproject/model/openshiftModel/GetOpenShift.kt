package com.development.allanproject.model.openshiftModel

data class GetOpenShift(
    val auth_token: String,
    val code: Int,
    val shifts: ArrayList<GetOpenShiftList>,
    val status: String,
    val success: Boolean,
    val user_id: Int
)