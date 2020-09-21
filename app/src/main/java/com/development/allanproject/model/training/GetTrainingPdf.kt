package com.development.allanproject.model.training

data class GetTrainingPdf(
    val additional: String,
    val code: Int,
    val `data`: Double,
    val status: String,
    val success: Boolean,
    var msg: String
)