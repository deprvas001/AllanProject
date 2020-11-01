package com.development.allanproject.model.missedShift

data class MissedShiftDetail(
    val address: String,
    val breaktime: String,
    val clock_in_time: String,
    val clock_out_time: String,
    val icon: String,
    val id: Int,
    val name: String,
    val start_date: String,
    val type: String,
    val type_icon: String,
    val update_time: Boolean,
    val waiting_for_approval: Boolean
)