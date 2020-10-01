package com.development.allanproject.model.preferenceModel

data class PreferenceData(
    val address: ArrayList<Addres>,
    val cities: ArrayList<Cities>,
    val days: ArrayList<Day>,
    val facilitiy_name: ArrayList<FacilitiyName>,
    val facilitiy_type: ArrayList<FacilitiyType>,
    val shift_type: ArrayList<ShiftType>
)