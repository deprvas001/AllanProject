package com.development.allanproject.model.commonapi

class EHRSDataType (
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}