package com.development.allanproject.model.dashboardModel

data class GetDashboard(
    val code: Int,
    val `data`: DashboardData,
    val status: String,
    val success: Boolean
)