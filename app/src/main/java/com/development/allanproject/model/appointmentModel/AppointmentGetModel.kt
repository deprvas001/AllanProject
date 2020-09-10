package com.development.allanproject.model.appointmentModel

class AppointmentGetModel(
    val success: Boolean,
    val status: String,
 //   val data: List<HashMap<String, String>>,
    /*val data: ArrayList<HashMap<String, HashMap<String,ArrayList<HashMap<String,String>>>>>,*/
    var data: ArrayList<AppointmentData>,
    val code: Int
) {

}