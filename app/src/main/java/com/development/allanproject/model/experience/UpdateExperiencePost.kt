package com.development.allanproject.model.experience

import com.google.gson.annotations.SerializedName

class UpdateExperiencePost (
    //    val details: Details,
    @SerializedName("details")
    var hashMap:HashMap<String,Any> = HashMap(),
    val step_no: Int,
    var api_action:String
) {
}