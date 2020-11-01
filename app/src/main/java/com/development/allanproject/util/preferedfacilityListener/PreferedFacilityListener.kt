package com.development.allanproject.util.preferedfacilityListener

import com.development.allanproject.model.pastShiftModel.MyPastShift
import com.development.allanproject.model.preferedfacility.GetPreferedFacility

interface PreferedFacilityListener {
    fun onStarted()
    fun onSuccess(response: GetPreferedFacility)
    fun onFailure(message: String)

}