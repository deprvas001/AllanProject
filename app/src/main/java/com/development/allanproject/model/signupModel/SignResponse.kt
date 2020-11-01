package com.development.allanproject.model.signupModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SignResponse(
    val auth_token: String,
    val step_no: Int,
    val success: Boolean,
    val user_id: Int,
    val code:String,
    val msg:String,
    val msg_noti :String,
    val shift_id:String,
    val is_sign_up_completed:Boolean,
    val cities:ArrayList<String>,
    val clocked_in_at:String,
    val break_time:String,
    val time_eclipsed:String,
    val is_break:Boolean,
    val status: String
)