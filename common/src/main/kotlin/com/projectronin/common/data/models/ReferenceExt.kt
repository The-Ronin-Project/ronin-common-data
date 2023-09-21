package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Reference

val Reference.isPatient: Boolean
    get() = reference!!.startsWith("Patient/")

val Reference.patientId: String?
    get() = when (isPatient) {
        true -> reference?.split("/")?.last()
        false -> null
    }
