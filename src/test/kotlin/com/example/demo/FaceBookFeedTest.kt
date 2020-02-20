import com.example.demo.integration.FacebookFeed
import io.mockk.every
import io.mockk.mockk
import okhttp3.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import okhttp3.ResponseBody.Companion.toResponseBody

class FaceBookFeedTest {

    var mockClient = mockk<OkHttpClient>();
    var facebookFeed = FacebookFeed("http://localhost:9000", mockClient)
    val request = Request.Builder().url("http://localhost:9000").build()

    @Test
    fun `should return the response data on success`() {

        every { mockClient.newCall(any()).execute() } returns
                Response.Builder().code(200).
                        request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .body("""[{"name":"jack","status":"i am a cat"}]""".toResponseBody()).build()

        val result = facebookFeed.getFeeds()

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("jack")
        assertThat(result[0].status).isEqualTo("i am a cat")
    }

    @Test
    fun `should return empty list on failure`() {
        every { mockClient.newCall(any()).execute() } returns
                Response.Builder().code(500)
                        .request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("").body("".toResponseBody()).build()

        val result = facebookFeed.getFeeds()

        assertThat(result.size).isEqualTo(0)
    }
}