package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Identifier

val Identifier.isTenantId: Boolean
    get() = system?.equals("http://projectronin.com/id/tenantId") ?: false

val Identifier.isDataAuthorityId: Boolean
    get() = system?.equals("http://projectronin.com/id/dataAuthorityId") ?: false

val Identifier.isFhirId: Boolean
    get() = system?.equals("http://projectronin.com/id/fhir") ?: false

val Collection<Identifier>.tenantId: String
    get() = requireNotNull(tenantIdOrNull) { "Tenant Id is null" }

val Collection<Identifier>.tenantIdOrNull: String?
    get() = firstOrNull { it.isTenantId }?.value

val Collection<Identifier>.dataAuthority: String
    get() = requireNotNull(dataAuthorityOrNull) { "Data Authority is null" }

val Collection<Identifier>.dataAuthorityOrNull: String?
    get() = firstOrNull { it.isDataAuthorityId }?.value

val Collection<Identifier>.fhirId: String
    get() = requireNotNull(fhirIdOrNull) { "Fhir Id is null" }

val Collection<Identifier>.fhirIdOrNull: String?
    get() = firstOrNull { it.isFhirId }?.value
