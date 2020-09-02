package com.development.allanproject.model.signupModel

import com.google.gson.annotations.SerializedName

class DocumentDetailPost(
//    val details: Details,
@SerializedName("details")
var hashMap:ArrayList<Any> = ArrayList<Any>(),
// val hashMap: HashMap<String,Any> = HashMap<String,Any>(),
val step_no: Int
)