package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Meta
import com.projectronin.fhir.r4.Resource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("HttpUrlsUsage")
class ResourceExtKtTest {
    @Nested
    inner class DisplayName {
        @Test
        fun `no profile and no id`() {
            val resource = Resource()

            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `handle only non letter or digits`() {
            val resource = Resource().apply {
                id = onlyNonLettersAndDigits
            }
            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `no profile but has id`() {
            val resource = Resource().apply {
                id = "unknown-resource-id"
            }
            assertThat(resource.displayName).isEqualTo("Unknown Resource Id")
        }

        @Test
        fun `handle consecutive non letter or digits at beginning, middle, and end`() {
            val resource = Resource().apply {
                id = "#!unknown-\$resource-!#id#$"
            }
            assertThat(resource.displayName).isEqualTo("Unknown Resource Id")
        }

        @Test
        fun `profile with empty list but has id`() {
            val resource = Resource().apply {
                meta = Meta().apply {
                    profile = emptyList()
                }
                id = "unknown-resource-id-89ba9c1f-e166-4f7a-b998-6c1c34a9f922"
            }
            assertThat(resource.displayName).isEqualTo("Unknown Resource Id 89ba9c1f E166 4f7a B998 6c1c34a9f922")
        }

        @Test
        fun `resource meta profile with all non letters and digits`() {
            val resource = resourceWithDisplayName(onlyNonLettersAndDigits)

            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `ronin-nlpDetectedVisit with one profile`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/StructureDefinition/ronin-nlpDetectedVisit")

            assertThat(resource.displayName).isEqualTo("Nlp Detected Visit")
        }

        @Test
        fun `ronin-nlpDetectedVisit picks first profile`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/StructureDefinition/ronin-nlpDetectedVisit", "http://another/profile")

            assertThat(resource.displayName).isEqualTo("Nlp Detected Visit")
        }

        @Test
        fun `ronin-nlpDetectedCancerStaging with one profile`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/StructureDefinition/ronin-nlpDetectedCancerStaging")

            assertThat(resource.displayName).isEqualTo("Nlp Detected Cancer Staging")
        }

        @Test
        fun `unhandled structured definition is prettified`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/StructureDefinition/mdaoc-conditionEncounterDiagnosis", "http://another/profile")

            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `unhandled non-structured definition starting with http projectronin io is prettified`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/SomeOtherDefinition/ronin-nlpDetectedCancerStaging")

            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `unhandled non-structured definition starting with http foo bar is prettified`() {
            val resource = resourceWithDisplayName("http://foo.bar/fhir/SomeOtherDefinition/ronin-nlpDetectedCancerStaging")

            assertThat(resource.displayName).isEqualTo("Unknown Resource")
        }

        @Test
        fun `unhandled structured definition is prettified and strips off ronin prefix`() {
            val resource = resourceWithDisplayName("http://projectronin.io/fhir/StructureDefinition/ronin-conditionEncounterDiagnosis", "http://another/profile")

            assertThat(resource.displayName).isEqualTo("Condition Encounter Diagnosis")
        }
    }
}

private fun resourceWithDisplayName(vararg name: String) = Resource().apply {
    meta = Meta().apply {
        profile = name.toList()
    }
}

private const val onlyNonLettersAndDigits = "#!-\$!##$"
