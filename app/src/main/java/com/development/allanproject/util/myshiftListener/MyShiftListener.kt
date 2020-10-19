package com.development.allanproject.util.myshiftListener

import com.development.allanproject.model.myshift.GetMyShift
import com.development.allanproject.model.openshiftModel.GetOpenShift

interface MyShiftListener {
    fun onStarted()
    fun onSuccess(response: GetMyShift)
    fun onFailure(message: String)

}