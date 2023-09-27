package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Attachment

val Attachment.isUrlAttachment: Boolean
    get() = url != null
