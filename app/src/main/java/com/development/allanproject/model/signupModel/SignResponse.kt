package com.development.allanproject.model.signupModel

data class SignResponse(
    val auth_token: String,
    val step_no: Int,
    val success: Boolean,
    val user_id: Int,
    val code:String,
    val status: String
)