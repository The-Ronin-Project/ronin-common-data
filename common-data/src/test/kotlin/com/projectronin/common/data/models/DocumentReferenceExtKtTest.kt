package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Attachment
import com.projectronin.fhir.r4.CodeableConcept
import com.projectronin.fhir.r4.Coding
import com.projectronin.fhir.r4.DocumentReference
import com.projectronin.fhir.r4.DocumentReference_Content
import com.projectronin.fhir.r4.Identifier
import com.projectronin.fhir.r4.Reference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class DocumentReferenceExtKtTest {
    private val tenantId = "apposnd"
    private val fhirId = "ehSXdmGPEBc--oKUrkz19VQ3"
    private val patientId = "apposnd-e63wRTbPfr1p8UW81d8Seiw3"
    private val dataAuthority = "EHR Data Authority"
    private val url = "https://images.squarespace-cdn.com/content/v1/" +
        "642adca97e8fbd450fafb96c/dccea417-fac7-4641-b301-8ccb2772f912/" +
        "Ronin+Logo+-+White+Text+Transparent+Bg+-+Large.png?format=1500w"

    private val docRef = DocumentReference().apply {
        subject = Reference().apply {
            type = "Patient"
            reference = "Patient/apposnd-e63wRTbPfr1p8UW81d8Seiw3"
        }
        identifier = listOf(
            Identifier().apply {
                system = "http://projectronin.com/id/tenantId"
                value = this@DocumentReferenceExtKtTest.tenantId
            },
            Identifier().apply {
                system = "http://projectronin.com/id/fhir"
                value = fhirId
            },
            Identifier().apply {
                system = "http://projectronin.com/id/dataAuthorityId"
                value = dataAuthority
            }
        )
        content = listOf(
            DocumentReference_Content().apply {
                attachment = Attachment().apply {
                    url = null
                }
            },
            DocumentReference_Content().apply {
                attachment = Attachment().apply {
                    url = this@DocumentReferenceExtKtTest.url
                }
            }
        )
        author = listOf(
            Reference().apply {
                type = "Practitioner"
                reference = "Practitioner/some-provider-id"
                display = "Dr. John Doe"
            },
            Reference().apply {
                type = "Practitioner"
                reference = "Practitioner/another-provider-id"
                display = "Dr. Jane Ronin"
            },
            Reference().apply {
                type = "NotProvider"
                reference = "NotProvider/some-not-provider-id"
            }
        )
        type = CodeableConcept().apply {
            coding = listOf(
                Coding().apply {
                    system = "http://loinc.org"
                    code = "TEST_CODE"
                }
            )
        }
    }

    @Test
    fun getPatientId() {
        assertThat(docRef.patientId).isEqualTo(patientId)
    }

    @Test
    fun getPatientIdThrows() {
        assertThrows<IllegalArgumentException> {
            DocumentReference().patientId
        }
    }

    @Test
    fun getTenantId() {
        assertThat(docRef.tenantId).isEqualTo(tenantId)
    }

    @Test
    fun getTenantIdThrows() {
        assertThrows<IllegalArgumentException> {
            DocumentReference().tenantId
        }
    }

    @Test
    fun getGetUrlAttachments() {
        val urlAttachments = docRef.getUrlAttachments()
        assertThat(urlAttachments.size).isEqualTo(1)
        assertThat(urlAttachments[0].url).isEqualTo(url)
    }

    @Test
    fun getAuthorsByTypeTest() {
        val providerAuthors = docRef.getAuthorsByType(ReferenceType.PRACTITIONER)
        assertThat(providerAuthors.size).isEqualTo(2)
        assertThat(providerAuthors[0].reference).isEqualTo("Practitioner/some-provider-id")

        val allAuthors = docRef.getAuthorsByType()
        assertThat(allAuthors.size).isEqualTo(3)
        assertThat(allAuthors[0].reference).isEqualTo("Practitioner/some-provider-id")
        assertThat(allAuthors[1].reference).isEqualTo("Practitioner/another-provider-id")
        assertThat(allAuthors[2].reference).isEqualTo("NotProvider/some-not-provider-id")

        assertThat(DocumentReference().getAuthorsByType().isEmpty()).isEqualTo(true)
    }

    @Test
    fun getAuthorDisplayNullTest() {
        assertThat(DocumentReference().authorDisplay).isEqualTo("")
    }

    @Test
    fun getAuthorDisplayTest() {
        assertThat(docRef.authorDisplay).isEqualTo("Dr. John Doe; Dr. Jane Ronin")
    }

    @Test
    fun getTypeLoinc() {
        assertThat(docRef.typeLoincCode).isEqualTo("TEST_CODE")
    }

    @Test
    fun getgetTypeLoincThrows() {
        assertThrows<IllegalArgumentException> {
            DocumentReference().typeLoincCode
        }
    }
}
