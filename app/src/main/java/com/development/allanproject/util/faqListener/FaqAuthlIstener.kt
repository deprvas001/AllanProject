package com.development.allanproject.util.faqListener

import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.faq.GetFaqList

interface FaqAuthlIstener {
    fun onStarted()
    fun onSuccess(response: GetFaqList)
    fun onFailure(message: String)
}