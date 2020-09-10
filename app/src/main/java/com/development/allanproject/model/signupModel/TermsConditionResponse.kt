package com.development.allanproject.model.signupModel

import com.development.allanproject.model.commonapi.Data

data class TermsConditionResponse(
    val tc: String,
    val `policy`: String,
    val success: Boolean
) {
}