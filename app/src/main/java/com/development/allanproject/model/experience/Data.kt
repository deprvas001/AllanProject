package com.development.allanproject.model.experience

data class Data(
    val address: String,
    val created_at: String,
    val end_date: String,
    val facility_id: Int,
    val facility_name: String,
    val id: Int,
    val nurse_id: Int,
    val verified_status: String,
    val reference: List<ReferenceListData>,
    val start_date: String,
    val status: Boolean,
    val sub_details: List<SubDetail>,
    val updated_at: String
)