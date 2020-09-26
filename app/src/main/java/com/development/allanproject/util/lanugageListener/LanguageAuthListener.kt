package com.development.allanproject.util.lanugageListener

import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.lanugage.GetLanugage

interface LanguageAuthListener{
    fun onStarted()
    fun onSuccess(response: GetLanugage)
    fun onFailure(message: String)

}