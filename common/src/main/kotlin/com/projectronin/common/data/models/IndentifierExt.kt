package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Identifier

val Identifier.isTenantId: Boolean
    get() = system?.equals("http://projectronin.com/id/tenantId") ?: false

val List<Identifier>.tenantId: String?
    get() = find { it.isTenantId }?.value

val Identifier.isDataAuthorityId: Boolean
    get() = system?.equals("http://projectronin.com/id/dataAuthorityId") ?: false

val List<Identifier>.dataAuthority: String?
    get() = find { it.isDataAuthorityId }?.value

val Identifier.isFhirId: Boolean
    get() = system?.equals("http://projectronin.com/id/fhir") ?: false

val List<Identifier>.fhirId: String?
    get() = find { it.isFhirId }?.value
