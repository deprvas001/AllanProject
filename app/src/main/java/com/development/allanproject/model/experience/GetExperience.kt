package com.development.allanproject.model.experience

data class GetExperience(
    val addtional: ArrayList<Any>,
    val code: Int,
    val `data`: ArrayList<Data>,
    val status: String,
    val success: Boolean
)