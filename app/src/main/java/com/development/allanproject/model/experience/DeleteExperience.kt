package com.development.allanproject.model.experience

import com.google.gson.annotations.SerializedName

class DeleteExperience(
    @SerializedName("details")
    var hashMap:HashMap<String,Int> = HashMap(),
    val step_no: Int,
    val api_action: String
) {
}