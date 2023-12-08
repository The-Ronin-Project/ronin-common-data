@file:Suppress("HttpUrlsUsage")

package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Resource

val Resource.displayName: String
    get() = getMetaProfileOrNull(id)?.let { prettyNameOrNull(it) }
        ?: "Unknown Resource"

fun Resource.getMetaProfileOrNull(defaultValue: String?): String? =
    meta?.profile?.firstOrNull { it.startsWith(projectRoninStructureDefinition) }
        ?.removePrefix(projectRoninStructureDefinition)
        ?: defaultValue

private fun prettyNameOrNull(value: String): String? =
    value.fold("") { acc, ch ->
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

private const val projectRoninStructureDefinition = "http://projectronin.io/fhir/StructureDefinition/ronin-"
