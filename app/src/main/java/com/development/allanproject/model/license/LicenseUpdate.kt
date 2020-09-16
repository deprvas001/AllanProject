package com.development.allanproject.model.license

import com.google.gson.annotations.SerializedName

class LicenseUpdate (
//    val details: Details,
    @SerializedName("details")
    var hashMap:HashMap<String,Any> =HashMap<String,Any>(),
    // val hashMap: HashMap<String,Any> = HashMap<String,Any>(),
    val step_no: Int,
    var api_action: String
)