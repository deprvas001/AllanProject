package com.development.allanproject.model.openshiftModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shift(
    val address: String,
    val end_date: String,
    val end_time: String,
    val facility_name: String,
    val facility_type: String,
    val icon: String,
    var facility_id:Int,
    val is_urgent: Boolean,
    val is_saved: Boolean,
    val lat: Double,
    val long: Double,
    val minimum_hours: String,
    val posted_date: String,
    val rate: String,
    val start_date: String,
    val start_time: String,
    val summary: String,
    val supervisor: String,
    val type: String,
    val type_icon: String,
    var category: String
): Parcelable