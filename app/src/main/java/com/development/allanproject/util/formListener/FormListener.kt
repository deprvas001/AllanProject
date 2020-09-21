package com.development.allanproject.util.formListener

import com.development.allanproject.model.form.GetFormList
import com.development.allanproject.model.healthDocument.HealthDocumentList

interface FormListener{
    fun onStarted()
    fun onSuccess(response: GetFormList)
    fun onFailure(message: String)

}