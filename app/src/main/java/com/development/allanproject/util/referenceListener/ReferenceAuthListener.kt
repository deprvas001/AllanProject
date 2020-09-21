package com.development.allanproject.util.referenceListener

import com.development.allanproject.model.reference.ReferenceList
import com.development.allanproject.model.speciality.GetSpeciality

interface ReferenceAuthListener {
    fun onStarted()
    fun onSuccess(response: ReferenceList)
    fun onFailure(message: String)
}