package com.development.allanproject.model.notificationModel

import com.google.gson.annotations.SerializedName

class PostNotificationSettings (
//    val details: Details,
    @SerializedName("details")
    var hashMap:HashMap<String,Any> = HashMap<String,Any>(),
    // val hashMap: HashMap<String,Any> = HashMap<String,Any>(),
    val step_no: Int
)