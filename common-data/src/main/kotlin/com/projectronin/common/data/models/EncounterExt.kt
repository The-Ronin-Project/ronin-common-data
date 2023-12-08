package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Encounter

val Encounter.displayName: String
    get() = getMetaProfileOrNull(id)?.let {
        nameMap[it]
    } ?: "Unknown Encounter"

private val nameMap = mapOf(
    "nlpDetectedVisit" to "ED Visit"
)
