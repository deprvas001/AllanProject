package com.development.allanproject.model.profileSummary

class ProfileSummaryGet(
    var success:Boolean,
    var data: ArrayList<ProfileSummaryData>,
    var status: String,
    var code: Int,
    var msg: String,
    var optional: ArrayList<ProfileSummaryData>
) {
}