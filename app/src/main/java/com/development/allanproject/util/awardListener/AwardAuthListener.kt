package com.development.allanproject.util.awardListener

import com.development.allanproject.model.award.GetAward
import com.development.allanproject.model.backinformation.GetBackgroundData

interface AwardAuthListener {

    fun onStarted()
    fun onSuccess(response: GetAward)
    fun onFailure(message: String)
}