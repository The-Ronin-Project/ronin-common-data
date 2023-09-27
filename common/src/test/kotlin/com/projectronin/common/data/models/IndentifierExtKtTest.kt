package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Identifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IndentifierExtKtTest {
    private val tenantId = "apposnd"
    private val fhirId = "ehSXdmGPEBc--oKUrkz19VQ3"
    private val dataAuthority = "EHR Data Authority"

    private val tenantIdentifier = Identifier().apply {
        system = "http://projectronin.com/id/tenantId"
        value = tenantId
    }
    private val fhirIdentifier = Identifier().apply {
        system = "http://projectronin.com/id/fhir"
        value = fhirId
    }
    private val dataAuthorityIdentifier = Identifier().apply {
        system = "http://projectronin.com/id/dataAuthorityId"
        value = dataAuthority
    }

    @Test
    fun isTenantId() {
        assertThat(tenantIdentifier.isFhirId).isEqualTo(false)
        assertThat(tenantIdentifier.isTenantId).isEqualTo(true)
        assertThat(tenantIdentifier.isDataAuthorityId).isEqualTo(false)
    }

    @Test
    fun getTenantId() {
        assertThat(listOf(dataAuthorityIdentifier, tenantIdentifier, fhirIdentifier).tenantId).isEqualTo(tenantId)
    }

    @Test
    fun isDataAuthorityId() {
        assertThat(dataAuthorityIdentifier.isFhirId).isEqualTo(false)
        assertThat(dataAuthorityIdentifier.isTenantId).isEqualTo(false)
        assertThat(dataAuthorityIdentifier.isDataAuthorityId).isEqualTo(true)
    }

    @Test
    fun getDataAuthority() {
        assertThat(listOf(dataAuthorityIdentifier, tenantIdentifier, fhirIdentifier).dataAuthority).isEqualTo(
            dataAuthority
        )
    }

    @Test
    fun isFhirId() {
        assertThat(fhirIdentifier.isFhirId).isEqualTo(true)
        assertThat(fhirIdentifier.isTenantId).isEqualTo(false)
        assertThat(fhirIdentifier.isDataAuthorityId).isEqualTo(false)
    }

    @Test
    fun getFhirId() {
        assertThat(listOf(dataAuthorityIdentifier, tenantIdentifier, fhirIdentifier).fhirId).isEqualTo(fhirId)
    }
}
