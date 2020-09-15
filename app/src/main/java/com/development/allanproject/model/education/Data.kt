package com.development.allanproject.model.education

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
    val address: String?,
    val created_at: String?,
    val degree: String?,
    val end_date: String?,
    val id: Int?,
    val institution: String?,
    val nurse_id: Int?,
    val start_date: String?,
    val status: Boolean?,
    val updated_at: String?,
    val verified_status: String?
) : Parcelable{

}