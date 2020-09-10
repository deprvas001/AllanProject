package com.development.allanproject.model.signupModel

data class SignResponse(
    val auth_token: String,
    val step_no: Int,
    val success: Boolean,
    val user_id: Int,
    val code:String,
    val msg:String,
    val is_sign_up_completed:Boolean,
    val cities:ArrayList<String>,
    val status: String
)