package com.development.allanproject.model.dashboardModel

data class DashboardData(
    val rating: Float,
    val rewards: Rewards,
    val this_week: ThisWeek,
    val user_lifetime: UserLifetime
)