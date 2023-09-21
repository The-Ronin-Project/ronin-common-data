package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Identifier

val Identifier.isTenantId: Boolean
    get() = system?.equals("http://projectronin.com/id/tenantId") ?: false

val List<Identifier>.tenantId: String?
    get() = find { it.isTenantId }?.value
