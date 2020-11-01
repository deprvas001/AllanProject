package com.development.allanproject.util.dashboardListener

import com.development.allanproject.model.clockoutModel.GetClockOutDetail
import com.development.allanproject.model.dashboardModel.GetDashboard

interface DashboardListener{

    fun onStarted()
    fun onSuccess(response: GetDashboard)
    fun onFailure(message: String)
}