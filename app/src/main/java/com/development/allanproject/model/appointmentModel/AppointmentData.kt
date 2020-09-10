package com.development.allanproject.model.appointmentModel

data class AppointmentData(
    var name:String,
    var appointment_details: ArrayList<AppointmentDetail>

) {

    override fun toString(): String {
        return name
    }
}