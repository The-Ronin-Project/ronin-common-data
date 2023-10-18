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
        require(isPatient)
        requireNotNull(reference?.id)
    }

val Reference.isProvider: Boolean
    get() = isType(PRACTITIONER)

val Reference.providerId: String
    get() = run {
        require(isProvider)
        requireNotNull(reference?.id)
    }

fun Reference.isType(referenceType: ReferenceType) = type?.equals(referenceType.type, true) ?: false

private val String.id
    get() = this.split("/").last()
