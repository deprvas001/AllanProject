package com.development.allanproject.util.bankListener

import com.development.allanproject.model.bankinfo.BankInfoResponse
import com.development.allanproject.model.certificate.CertificateList

interface BankAuthListener {
    fun onStarted()
    fun onSuccess(response:BankInfoResponse)
    fun onFailure(message: String)

}