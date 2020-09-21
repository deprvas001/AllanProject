package com.development.allanproject.model.bankinfo

class BankInfoResponse(
    var success: Boolean,
    var status: String,
    var code: Int,
    var data:ArrayList<BankData>
) {
}