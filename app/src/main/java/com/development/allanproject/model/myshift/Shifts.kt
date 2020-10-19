package com.development.allanproject.model.myshift

data class Shifts(
    val future: List<ShiftItem>,
    val past: List<ShiftItem>,
    val today: List<ShiftItem>
)