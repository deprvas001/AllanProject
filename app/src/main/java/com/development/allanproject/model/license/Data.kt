package com.development.allanproject.model.license

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val created_at: String?,
    val expiration_date: String?,
    val id: Int?,
    val img_url: List<String>?,
    val issue_date: String?,
    val licence_compact: String?,
    val licence_id: Int?,
    val licence_no: String?,
    val nurse_id: Int?,
    val state: String?,
    val status: Boolean?,
    val updated_at: String?,
    val verified_status: String?
):Parcelable