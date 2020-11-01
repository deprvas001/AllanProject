package com.development.allanproject.model.hiddenjobs

data class GetHiddenJobs(
    val code: Int,
    val shifts: List<GetHiddenShift>,
    val status: String,
    val success: Boolean
)