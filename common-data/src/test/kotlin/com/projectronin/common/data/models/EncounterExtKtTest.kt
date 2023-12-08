package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Encounter
import com.projectronin.fhir.r4.Meta
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EncounterExtKtTest {
    @Test
    fun `it works`() {
        val encounter = buildEncounter("http://projectronin.io/fhir/StructureDefinition/ronin-nlpDetectedVisit")

        assertThat(encounter.displayName).isEqualTo("ED Visit")
    }

    @Test
    fun `other works`() {
        val encounter = buildEncounter("http://projectronin.io/fhir/StructureDefinition/ronin-nlpSomethingElse")

        assertThat(encounter.displayName).isEqualTo("Unknown Encounter")
    }

    @Test
    fun `no profiles works`() {
        val encounter = Encounter()

        assertThat(encounter.displayName).isEqualTo("Unknown Encounter")
    }
}

private fun buildEncounter(vararg profiles: String) = Encounter().apply {
    meta = Meta().apply {
        profile = profiles.toList()
    }
}
