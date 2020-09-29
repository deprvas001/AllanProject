package com.development.allanproject.util.i9formListener

import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.i9form.GetI9Form

interface I9FormListener {
    fun onStarted()
    fun onSuccess(response: GetI9Form)
    fun onFailure(message: String)

}