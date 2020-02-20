import com.example.demo.integration.InstagramFeed
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InstagramFeedTest {
    var mockClient = mockk<OkHttpClient>();
    var instagramFeed = InstagramFeed("http://localhost:9000", mockClient)
    val request = Request.Builder().url("http://localhost:9000").build()

    @Test
    fun `should return the response data on success`() {
        every { mockClient.newCall(any()).execute() } returns
                Response.Builder().code(200).request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .body("""[{"username":"simon","picture":"cat"}]""".toResponseBody()).build()

        val result = instagramFeed.getFeeds()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].username).isEqualTo("simon")
        assertThat(result[0].picture).isEqualTo("cat")
    }

    @Test
    fun `should return empty list on failure`() {
        every { mockClient.newCall(any()).execute() } returns
                Response.Builder().code(500)
                        .request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .body("".toResponseBody()).build()

        val result = instagramFeed.getFeeds()
        assertThat(result.size).isEqualTo(0)
    }
}