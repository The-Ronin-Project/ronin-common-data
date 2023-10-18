package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Coding

val Coding.isLoinc: Boolean
    get() = system?.equals("http://loinc.org", ignoreCase = true) == true
