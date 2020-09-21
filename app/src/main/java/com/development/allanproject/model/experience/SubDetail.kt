package com.development.allanproject.model.experience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubDetail(
    val charge_experience: Boolean,
    val charting_technology: String,
    val created_at: String,
    val experience_id: Int,
    val id: Int,
    val position: String,
    val position_month: String,
    val position_year: String,
    val speciality: String,
    val status: Boolean,
    val unit: String,
    val updated_at: String
):Parcelable