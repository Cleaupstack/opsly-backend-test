import com.example.demo.DemoApplication
import com.example.demo.domain.FeedDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.client.WireMock.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = [DemoApplication::class])
@AutoConfigureWireMock(port = 9000)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("it")
class DemoAppIntegrationTest {

    val testClient = OkHttpClient()

    var request = Request.Builder()
            .url("http://localhost:5000")
            .build()

    @Test
    fun `should return data in the correct format for all feeds`() {
        stubAndReturnData()
        val call = testClient.newCall(request)
        val response = call.execute()
        assertThat(response.code).isEqualTo(200)
        val resp = jacksonObjectMapper().readValue<FeedDTO>(response.body!!.string())
        assertThat(resp.facebook.size).isEqualTo(1)
        assertThat(resp.twitter.size).isEqualTo(1)
        assertThat(resp.instagram.size).isEqualTo(1)
    }

    fun stubAndReturnData() {
        stubFor(get(urlEqualTo("/facebook"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""[{"name":"jack","status":"i am a cat"}]""")))

        stubFor(get(urlEqualTo("/instagram"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""[{"username":"jack","picture":"its a cat cat"}]""")))

        stubFor(get(urlEqualTo("/twitter"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody("""[{"username":"jill","tweet":"i have a bad headache"}]""")))
    }
}