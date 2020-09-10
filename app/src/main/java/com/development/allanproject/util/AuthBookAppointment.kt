package com.development.allanproject.util

import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.signupModel.SignResponse

interface AuthBookAppointment {

    fun onStarted()
    fun onSuccess(response: AppointmentGetModel)
    fun onFailure(message: String)

}