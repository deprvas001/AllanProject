package com.development.allanproject.model.preferedfacility

data class FacilityData(
    val address: String,
    val id: Int,
    val img_url: String,
    val isFav: Boolean,
    val name: String,
    val rating: Float
)