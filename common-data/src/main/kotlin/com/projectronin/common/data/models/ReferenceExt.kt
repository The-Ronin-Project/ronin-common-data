package com.projectronin.common.data.models

import com.projectronin.common.data.models.ReferenceType.PATIENT
import com.projectronin.common.data.models.ReferenceType.PRACTITIONER
import com.projectronin.fhir.r4.Reference

enum class ReferenceType(val type: String) {
    PATIENT("Patient"),
    PRACTITIONER("Practitioner")
}

val Reference.isPatient: Boolean
    get() = isType(PATIENT)

val Reference.patientId: String
    get() = run {
        require(isPatient) { "not a patient" }
        requireNotNull(reference?.id) { "patient id is missing" }
    }

val Reference.isProvider: Boolean
    get() = isType(PRACTITIONER)

val Reference.providerId: String
    get() = run {
        require(isProvider) { "not a provider" }
        requireNotNull(reference?.id) { "provider id is missing" }
    }

fun Reference.isType(referenceType: ReferenceType) = type?.equals(referenceType.type, true) ?: false

private val String.id
    get() = this.split("/").last()
