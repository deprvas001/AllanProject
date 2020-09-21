package com.development.allanproject.model.adddocumentModel

data class PostDocument(
    val api_action: String,
    val details: HashMap<String,Any>,
    val step_no: Int
)