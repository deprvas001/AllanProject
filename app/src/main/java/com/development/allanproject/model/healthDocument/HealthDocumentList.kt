package com.development.allanproject.model.healthDocument

class HealthDocumentList(
    var success:Boolean,
    var data: ArrayList<HealthDocumentData>,
    var status:String,
    var code: Int
) {
}