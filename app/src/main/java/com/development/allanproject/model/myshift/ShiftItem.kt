package com.development.allanproject.model.myshift

import com.google.gson.annotations.SerializedName

data class ShiftItem(
    val address: String,
    val distance: String,
    val facility_name: String,
    val icon: String,
    val id: Int,
    var facility_id:Int,
    val myshift_type: String,
    val price: String,
    val start_date: String,
    val time: String,
    val type: String,
    val type_icon: String,

    var category:String,
    var name:String,
    var date:String

)