package com.development.allanproject.util.documentListener

import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.education.EducationListApiResonse

interface DocumentListener {

    fun onStarted()
    fun onSuccess(response: GetDocumentSpinner)
    fun onFailure(message: String)
}