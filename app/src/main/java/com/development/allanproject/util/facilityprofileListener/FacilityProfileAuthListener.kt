package com.development.allanproject.util.facilityprofileListener

import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.facilityprofileModel.GetFacilityProfile

interface FacilityProfileAuthListener {
    fun onStarted()
    fun onSuccess(response: GetFacilityProfile)
    fun onFailure(message: String)
}