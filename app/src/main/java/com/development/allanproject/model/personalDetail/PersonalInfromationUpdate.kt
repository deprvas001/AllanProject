package com.development.allanproject.model.personalDetail

import com.google.gson.annotations.SerializedName

class PersonalInfromationUpdate(
    @SerializedName("details")
    var hashMap:HashMap<String,String> = HashMap(),
    val step_no: Int,
    val api_action: String
    )

{
}