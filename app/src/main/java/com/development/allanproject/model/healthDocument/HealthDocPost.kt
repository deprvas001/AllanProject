package com.development.allanproject.model.healthDocument

import com.google.gson.annotations.SerializedName

class HealthDocPost(
    @SerializedName("details")
    var hashMap:HashMap<String,Any> = HashMap(),
    val step_no: Int,
    val api_action:String
) {
}