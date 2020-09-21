package com.development.allanproject.model.experience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ReferenceListData(
    var id:Int,
    var experience_id: Int,
    var facility_name:String,
    var name:String,
    var job_title:String,
    var job_type: String,
    var phone: String,
    var email: String,
    var status: Boolean,
    var created_at: String,
    var updated_at: String,
    var std_code: String,
    var nurse_id: String

):Parcelable {
}