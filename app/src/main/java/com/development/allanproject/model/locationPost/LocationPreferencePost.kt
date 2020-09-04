package com.development.allanproject.model.locationPost

import com.google.gson.annotations.SerializedName

class LocationPreferencePost(
    //    val details: Details,
    @SerializedName("details")
    var hashMap:HashMap<String,Any> = HashMap(),
    val step_no: Int
) {
}