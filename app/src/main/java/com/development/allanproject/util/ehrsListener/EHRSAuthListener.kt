package com.development.allanproject.util.ehrsListener

import com.development.allanproject.model.ehrs.EHRSData
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.speciality.GetSpeciality

interface EHRSAuthListener {
    fun onStarted()
    fun onSuccess(response: EHRSList)
    fun onFailure(message: String)
}