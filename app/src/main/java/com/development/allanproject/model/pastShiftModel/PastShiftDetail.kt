package com.development.allanproject.model.pastShiftModel

data class PastShiftDetail(
    val address: String,
    val category: String,
    val clock_in_time: String,
    val clock_out_time: String,
    val earned: String,
    val end_date: String,
    val end_time: String,
    val facility_id: Int,
    val facility_name: String,
    val facility_type: String,
    val icon: String,
    val is_paid: Boolean,
    val id:Int,
    val is_urgent: Boolean,
    val minimum_hours: String,
    val premium: String,
    val rate: String,
    val start_date: String,
    val start_time: String,
    val type: String,
    val type_icon: String,
    val wage: String,
    val is_facility_fav: Boolean
)