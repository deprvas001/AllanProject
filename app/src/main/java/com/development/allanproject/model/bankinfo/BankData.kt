package com.development.allanproject.model.bankinfo

data class BankData(
    val account_no: String,
    val created_at: String,
    val id: Int,
    val nurse_id: Int,
    val routing_no: String,
    val status: Boolean,
    val updated_at: String
)