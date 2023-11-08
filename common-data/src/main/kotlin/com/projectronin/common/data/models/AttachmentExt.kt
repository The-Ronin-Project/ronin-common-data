package com.projectronin.common.data.models

import com.projectronin.fhir.r4.Attachment
import com.projectronin.fhir.r4.Element
import com.projectronin.fhir.r4.Extension
import java.net.URL

val Attachment.isUrlAttachment: Boolean
    get() = url != null

val Attachment.ehrdaUrlOrNull: URL?
    get() = run {
        require(isUrlAttachment) { "not a url attachment" }
        _url?.extension?.findEhrdaAttachmentUrlOrNull()?.valueUrl?.let { URL(it) }
    }

val Attachment.ehrdaUrl: URL
    get() = requireNotNull(ehrdaUrlOrNull) { "ehrdaUrl was not found" }

fun Attachment.setEhrdaUrl(url: URL): Attachment {
    require(isUrlAttachment) { "not a url attachment" }
    if (this._url == null) {
        this._url = Element()
    }

    val urlElement = requireNotNull(this._url) { "_url is not found" }
    if (urlElement.extension == null) {
        this.extension = mutableListOf()
    }

    val extensionElement = requireNotNull(urlElement.extension) { "_url extension not found" }
    extensionElement.findEhrdaAttachmentUrlOrNull()?.also {
        require(extensionElement.remove(it)) { "failed to remove existing ehrdaAttachmentUrl" }
    }

    extensionElement.add(buildEhrdaAttachmentUrl(url))
    return this
}

private fun buildEhrdaAttachmentUrl(valueURL: URL) = Extension().apply {
    this.url = EHRDA_ATTACHMENT_URL
    this.valueUrl = valueURL.toString()
    this.extension = emptyList()
}

private fun MutableList<Extension>.findEhrdaAttachmentUrlOrNull() =
    firstOrNull { it.url == EHRDA_ATTACHMENT_URL }

private const val EHRDA_ATTACHMENT_URL = "http://projectronin.io/fhir/StructureDefinition/Extension/ehrdaAttachmentURL"
