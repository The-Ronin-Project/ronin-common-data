import com.projectronin.common.data.models.isUrlAttachment
import com.projectronin.fhir.r4.Attachment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AttachmentExtKtTest {

    @Test
    fun `test is url attachment`() {
        val a = Attachment().apply {
            url = "asdf"
        }
        assertThat(a.isUrlAttachment).isEqualTo(true)
    }

    @Test
    fun `test is not url attachment`() {
        val a = Attachment().apply {
            url = null
        }
        assertThat(a.isUrlAttachment).isEqualTo(false)
    }
}
