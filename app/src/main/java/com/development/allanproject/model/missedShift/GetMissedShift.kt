package com.development.allanproject.model.missedShift

data class GetMissedShift(
    val code: Int,
    val shifts: List<MissedShiftDetail>,
    val status: String,
    val success: Boolean
)