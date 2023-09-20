package com.projectronin.common.data.models

import com.projectronin.fhir.r4.DocumentReference

val DocumentReference.patientId: String?
    get() = subject?.patientId

val DocumentReference.tenantId: String?
    get() = identifier?.tenantId