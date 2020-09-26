package com.development.allanproject.util.backgronddata

import com.development.allanproject.model.backinformation.GetBackgroundData
import com.development.allanproject.model.bankinfo.BankInfoResponse

interface BackgroundAuthListener {
    fun onStarted()
    fun onSuccess(response: GetBackgroundData)
    fun onFailure(message: String)

}