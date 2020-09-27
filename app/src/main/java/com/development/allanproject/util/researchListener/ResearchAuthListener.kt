package com.development.allanproject.util.researchListener

import com.development.allanproject.model.backinformation.GetBackgroundData
import com.development.allanproject.model.research.GetResearch

interface ResearchAuthListener {
    fun onStarted()
    fun onSuccess(response: GetResearch)
    fun onFailure(message: String)

}