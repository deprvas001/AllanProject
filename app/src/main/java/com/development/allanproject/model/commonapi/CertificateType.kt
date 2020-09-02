package com.development.allanproject.model.commonapi

class CertificateType (
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
