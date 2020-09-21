package com.development.allanproject.model.socialsecurity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SecurityData(
    val doc_back: String,
    val doc_front: String,
    val id: Int,
    val name: String,
    val verified_status: String
): Parcelable