@file:Suppress("HttpUrlsUsage")

package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Resource

val Resource.displayName: String
    get() = getMetaProfileOrNull(id)?.let { value ->
        prettyNameOrNull(metaProfileDisplayNameMap.getOrDefault(value, value))
    } ?: unknownResourceDisplayName

private fun Resource.getMetaProfileOrNull(defaultValue: String?): String? =
    meta?.profile?.firstOrNull() ?: defaultValue

private fun prettyNameOrNull(value: String): String? =
    removePrefixes(value).fold("") { acc, ch ->
        if (acc.isEmpty()) {
            if (ch.isLetterOrDigit()) {
                // always capitalize first char
                ch.uppercase()
            } else {
                // ignore leading non letter or digits
                ""
            }
        } else if (!ch.isLetterOrDigit()) {
            // replace consecutive non letter or digit with only one space
            if (!acc.last().isWhitespace()) {
                "$acc "
            } else {
                acc
            }
        } else {
            val last = acc.last()
            if (last.isWhitespace()) {
                "$acc${ch.uppercase()}"
            } else if (last.isUpperCase()) {
                "$acc$ch"
            } else if (ch.isUpperCase()) {
                "$acc $ch"
            } else {
                "$acc$ch"
            }
        }
    }.trim().takeIf { it.isNotEmpty() }

private fun removePrefixes(value: String): String = knownPrefixes.fold(value) { acc, prefix ->
    acc.removePrefix(prefix)
}

private val metaProfileDisplayNameMap = mapOf(
    "http://projectronin.io/fhir/StructureDefinition/ronin-nlpDetectedVisit" to "ED Visit"
)

private val knownPrefixes = arrayOf(
    "http://projectronin.io",
    "/fhir/StructureDefinition/",
    "ronin-",
    "nlp"
)
private const val unknownResourceDisplayName = "Unknown Resource"
