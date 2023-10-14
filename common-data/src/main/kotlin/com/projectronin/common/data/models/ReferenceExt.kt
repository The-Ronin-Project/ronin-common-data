package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Reference

val Reference.isPatient: Boolean
    get() = type?.equals("Patient") == true

val Reference.patientId: String
    get() = run {
        require(isPatient)
        requireNotNull(reference?.id)
    }

val Reference.isProvider: Boolean
    get() = type?.equals("Practitioner") == true

val Reference.providerId: String
    get() = run {
        require(isProvider)
        requireNotNull(reference?.id)
    }

private val String.id
    get() = this.split("/").last()
