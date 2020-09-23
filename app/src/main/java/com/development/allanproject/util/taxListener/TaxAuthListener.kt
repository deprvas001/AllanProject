package com.development.allanproject.util.taxListener

import com.development.allanproject.model.taxholding.GetTaxHolding

interface TaxAuthListener {
    fun onStarted()
    fun onSuccess(response: GetTaxHolding)
    fun onFailure(message: String)

}