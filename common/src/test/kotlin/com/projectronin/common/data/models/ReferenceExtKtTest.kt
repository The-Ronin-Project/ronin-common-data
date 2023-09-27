package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Reference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReferenceExtKtTest {
    private val patient = Reference().apply {
        type = "Patient"
        reference = "Patient/apposnd-e63wRTbPfr1p8UW81d8Seiw3"
    }

    private val provider = Reference().apply {
        type = "Practitioner"
        reference = "Practitioner/apposnd-eMzoJlqFZvr3SNTyrDPxLYqHNVs407xu0VeXPC8I35U83"
    }

    @Test
    fun `test Patient`() {
        assertThat(patient.isPatient).isEqualTo(true)
        assertThat(patient.isProvider).isEqualTo(false)
        assertThat(patient.patientId).isEqualTo("apposnd-e63wRTbPfr1p8UW81d8Seiw3")
    }

    @Test
    fun `test Provider`() {
        assertThat(provider.isPatient).isEqualTo(false)
        assertThat(provider.isProvider).isEqualTo(true)
        assertThat(provider.providerId).isEqualTo("apposnd-eMzoJlqFZvr3SNTyrDPxLYqHNVs407xu0VeXPC8I35U83")
    }
}
