package com.development.allanproject.util.healthdocListener

import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.healthDocument.HealthDocumentList

interface HealthDocAuthListener {
    fun onStarted()
    fun onSuccess(response: HealthDocumentList)
    fun onFailure(message: String)

}