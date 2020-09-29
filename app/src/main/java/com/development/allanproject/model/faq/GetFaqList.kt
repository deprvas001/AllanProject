package com.development.allanproject.model.faq

data class GetFaqList(
    val `data`: List<FaqData>,
    val success: Boolean
)