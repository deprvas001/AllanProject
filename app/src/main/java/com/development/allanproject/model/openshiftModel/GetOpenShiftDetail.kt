package com.development.allanproject.model.openshiftModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetOpenShiftDetail(
    val auth_token: String,
    val code: Int,
    val shift: Shift,
    val status: String,
    val success: Boolean,
    val user_id: Int
):Parcelable