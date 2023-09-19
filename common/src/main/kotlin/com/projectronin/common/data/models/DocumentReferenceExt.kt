package com.projectronin.common.data.models

import com.projectronin.fhir.r4.DocumentReference

val DocumentReference.tenantId: String?
    get() = identifier?.find { it.system?.contains("http://projectronin.com/id/tenantId") ?: false }?.value

val DocumentReference.patientId: String?
    get() = run {
        if (!(subject!!.reference!!.startsWith("Patient/"))) return null
        return subject?.reference?.split("/")?.last()
    }
