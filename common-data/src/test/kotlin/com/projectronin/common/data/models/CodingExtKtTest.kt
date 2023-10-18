package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Coding
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CodingExtKtTest {
    private val loincCode = Coding().apply {
        system = "http://loinc.org"
        version = "2.74"
        code = "11506-3"
        display = "Progress note"
    }

    private val fhirCode = Coding().apply {
        system = "http://projectronin.com/id/fhir"
        code = "FHIR ID"
        display = "FHIR Identifier"
    }

    @Test
    fun isLoincTest() {
        assertThat(loincCode.isLoinc).isEqualTo(true)
        assertThat(fhirCode.isLoinc).isEqualTo(false)
    }

    @Test
    fun isLoincFalseWhenNull() {
        assertThat(Coding().isLoinc).isEqualTo(false)
    }
}
