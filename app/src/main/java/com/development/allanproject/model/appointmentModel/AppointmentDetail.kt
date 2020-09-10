package com.development.allanproject.model.appointmentModel

data class AppointmentDetail(
    var date:String,
    var slots:ArrayList<HashMap<String,String>>
) {
}