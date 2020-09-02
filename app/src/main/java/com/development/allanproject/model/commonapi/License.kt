package com.development.allanproject.model.commonapi

data class License(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}