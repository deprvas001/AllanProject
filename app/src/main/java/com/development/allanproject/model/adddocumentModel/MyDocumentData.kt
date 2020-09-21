package com.development.allanproject.model.adddocumentModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyDocumentData(
    val created_at: String,
    val doc_back: String,
    val doc_front: String,
    val doc_type: String,
    val id: Int,
    val name: String,
    val nurse_id: Int,
    val status: Boolean,
    val updated_at: String,
    val verified_status: String
):Parcelable