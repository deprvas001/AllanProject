package com.development.allanproject.model.certificate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CertificateData(
    var id: Int?,
    var nurse_id: String?,
    var certificate_id: Int?,
    var status: Boolean?,
    var created_at: String?,
    var updated_at: String?,
    var issued_from: String?,
    var issue_date: String?,
    var end_date:String?,
    var img_urls: ArrayList<String>?,
    var verified_status: String?
) : Parcelable{
}