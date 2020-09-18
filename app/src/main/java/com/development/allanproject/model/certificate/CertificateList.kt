package com.development.allanproject.model.certificate

class CertificateList(
    var success: Boolean,
    var status: String,
    var code: Int?,
    var data: ArrayList<CertificateData>
) {
}