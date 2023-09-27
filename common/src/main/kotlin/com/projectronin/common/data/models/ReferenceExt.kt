package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Reference

val Reference.isPatient: Boolean
    get() = type?.equals("Patient") == true

val Reference.patientId: String?
    get() = when (isPatient) {
        true -> reference?.id
        false -> null
    }

val Reference.isProvider: Boolean
    get() = type?.equals("Practitioner") == true

val Reference.providerId: String?
    get() = when (isProvider) {
        true -> reference?.id
        false -> null
    }

private val String.id
    get() = this.split("/").last()
