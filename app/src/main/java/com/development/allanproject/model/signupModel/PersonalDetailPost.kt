package com.development.allanproject.model.signupModel

import com.google.gson.annotations.SerializedName

data class PersonalDetailPost(
//    val details: Details,
    @SerializedName("details")
    var hashMap:ArrayList<HashMap<String,Any>> = ArrayList<HashMap<String,Any>>(),
   // val hashMap: HashMap<String,Any> = HashMap<String,Any>(),
    val step_no: Int
)