package com.development.allanproject.model.personalDetail

import com.google.gson.annotations.SerializedName

class PersonalDetailPostParam (
//    val details: Details,
    @SerializedName("details")
    var hashMap:HashMap<String,Any> = HashMap<String,Any>(),
    // val hashMap: HashMap<String,Any> = HashMap<String,Any>(),
    val step_no: Int
)