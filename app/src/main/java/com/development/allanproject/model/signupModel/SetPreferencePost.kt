package com.development.allanproject.model.signupModel

import com.google.gson.annotations.SerializedName

class SetPreferencePost (
    @SerializedName("shift_preferances")
    var shiftPreferenceList:ArrayList<Int> = ArrayList<Int>(),

    @SerializedName("shift_type")
    var shiftTypeList:ArrayList<Int> = ArrayList<Int>(),

    val step_no: Int
)