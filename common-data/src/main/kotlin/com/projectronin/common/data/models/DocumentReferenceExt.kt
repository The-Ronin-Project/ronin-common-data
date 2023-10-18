package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Attachment
import com.projectronin.fhir.r4.DocumentReference
import com.projectronin.fhir.r4.Reference

val DocumentReference.patientId: String
    get() = requireNotNull(subject?.patientId) { "Subject is null" }

val DocumentReference.tenantId: String
    get() = requireNotNull(identifier?.tenantId) { "Identifier is null" }

fun DocumentReference.getUrlAttachments(): List<Attachment> = content
    .filter { it.attachment.isUrlAttachment }
    .map { content -> content.attachment }

fun DocumentReference.getAuthorsByType(referenceType: ReferenceType? = null): List<Reference> {
    return this.author?.let {
        when (referenceType?.type) {
            is String -> it.filter { author: Reference -> author.isType(referenceType) }
            else -> it
        }
    }?.toList() ?: emptyList()
}
