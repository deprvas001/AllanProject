package com.development.allanproject.model.ehrs

import com.development.allanproject.model.speciality.Data

class EHRSList(
    val code: Int,
    val `data`: ArrayList<EHRSData>,
    val status: String,
    val success: Boolean
)