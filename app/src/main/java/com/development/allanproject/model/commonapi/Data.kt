package com.development.allanproject.model.commonapi

data class Data(
    val facility_preference: List<FacilityPreference>,
    val facility_type: List<FacilityType>,
    val license: List<License>,
    val shift_preference: List<ShiftPreference>,
    val shift_type: List<ShiftType>,
    val speciality: List<Speciality>,
    val states: List<String>,
    val certificates: List<CertificateType>
)