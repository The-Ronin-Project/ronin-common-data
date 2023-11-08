import com.projectronin.common.data.models.ehrdaUrl
import com.projectronin.common.data.models.ehrdaUrlOrNull
import com.projectronin.common.data.models.isUrlAttachment
import com.projectronin.common.data.models.setEhrdaUrl
import com.projectronin.fhir.r4.Attachment
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.net.URL

class AttachmentExtKtTest {

    @Nested
    inner class IsUrlAttachment {
        @Test
        fun `test is url attachment`() {
            assertThat(validAttachment().isUrlAttachment).isTrue()
        }

        @Test
        fun `test is not url attachment`() {
            assertThat(nonUrlAttachment().isUrlAttachment).isFalse()
        }
    }

    @Nested
    inner class EhrdaUrlOrNull {
        @Test
        fun `throws error when not attachment`() {
            val message = assertThrows<IllegalArgumentException> { nonUrlAttachment().ehrdaUrl }.message

            assertThat(message).isEqualTo("not a url attachment")
        }

        @Test
        fun `works when null`() {
            assertThat(validAttachment().ehrdaUrlOrNull).isNull()
        }

        @Test
        fun `works when not null`() {
            val expectedUrl = URL("http://foo")

            assertThat(validAttachment().setEhrdaUrl(expectedUrl).ehrdaUrlOrNull).isEqualTo(expectedUrl)
        }
    }

    @Nested
    inner class EhrdaUrl {
        @Test
        fun `throws error when not attachment`() {
            val message = assertThrows<IllegalArgumentException> { nonUrlAttachment().ehrdaUrl }.message

            assertThat(message).isEqualTo("not a url attachment")
        }

        @Test
        fun `throws error when null`() {
            val message = assertThrows<IllegalArgumentException> { validAttachment().ehrdaUrl }.message

            assertThat(message).isEqualTo("ehrdaUrl was not found")
        }

        @Test
        fun `works when not null`() {
            val expectedUrl = URL("http://foo")

            assertThat(validAttachment().setEhrdaUrl(expectedUrl).ehrdaUrl).isEqualTo(expectedUrl)
        }
    }

    @Nested
    inner class SetEhrdaUrl {
        @Test
        fun `throws error when not attachment`() {
            val message = assertThrows<IllegalArgumentException> {
                nonUrlAttachment().setEhrdaUrl(URL("http://foo"))
            }.message

            assertThat(message).isEqualTo("not a url attachment")
        }

        @Test
        fun `works when no existing value`() {
            // This is tested in the other unit tests
        }

        @Test
        fun `works when already has existing value`() {
            val a = validAttachment()
                .setEhrdaUrl(URL("https://one"))
                .setEhrdaUrl(URL("https://two"))
                .setEhrdaUrl(URL("https://three"))

            assertThat(a.ehrdaUrl).isEqualTo(URL("https://three"))

            val extension = a._url?.extension ?: fail("_url.extension was not set")

            assertThat(extension).hasSize(1)
            with(extension.first()) {
                assertThat(this.url).isEqualTo("http://projectronin.io/fhir/StructureDefinition/Extension/ehrdaAttachmentURL")
                assertThat(this.valueUrl).isEqualTo("https://three")
            }
        }
    }
}

private fun validAttachment() = attachment("http://asdf")
private fun nonUrlAttachment() = attachment()

private fun attachment(url: String? = null, init: Attachment.() -> Unit = {}) = Attachment().apply {
    this.url = url?.let { URL(it) }?.toString()
    init()
}
