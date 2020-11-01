package com.development.allanproject.model.clockoutModel

data class ClockOutShift(
    val breaktime: String,
    val checkintime: String,
    val checkouttime: String,
    val shift_id: String,
    val supervisor: String,
    val time_eclipsed: String
)